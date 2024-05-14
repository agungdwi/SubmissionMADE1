package com.example.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.entity.MovieEntity
import com.example.core.data.local.entity.RemoteKeys
import com.example.core.data.local.room.DatabaseTransactionHelper
import com.example.core.data.remote.network.ApiService
import com.example.core.utils.DataMapper

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val apiService: ApiService,
    private val localDataSource: LocalDataSource,
    private val transactionHelper: DatabaseTransactionHelper


) : RemoteMediator<Int, MovieEntity>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = apiService.getMovies(page).results
            val endOfPaginationReached = responseData.isEmpty()
            val movieList = DataMapper.mapResponseToEntity(responseData)

            transactionHelper.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteRemoteKeys()
                    localDataSource.deleteAllMovies()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    RemoteKeys(id = it.id.toString(), prevKey = prevKey, nextKey = nextKey)
                }

                localDataSource.insertMovies(movieList)
                localDataSource.insertRemoteKeys(keys)
            }


            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id.toString())
        }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id.toString())
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeysId(id.toString())
            }
        }
    }

    }



