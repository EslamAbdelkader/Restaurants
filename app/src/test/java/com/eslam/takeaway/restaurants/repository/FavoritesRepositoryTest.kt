package com.eslam.takeaway.restaurants.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class FavoritesRepositoryTest {

    private lateinit var dataSource : IFavoritesDataSource

    private lateinit var repo: FavoritesRepository

    @Before
    fun setUp() {
        repo = FavoritesRepository()
        dataSource = mock()

        repo.dataSource = dataSource
    }

    @Test
    fun `fetch method fetches all items from data source`(){
        // Given
        val set = setOf("dummy1", "dummy2", "dummy3")
        whenever(dataSource.fetchFavorites()).thenReturn(set)

        // When
        val testObserver = repo.fetchAllFavorites().test()

        // Then
        assertThat(testObserver.values()).containsExactly(set)
    }

    @Test
    fun `An observer gets notified whenever a new item is inserted`(){
        // Given
        val set = mutableSetOf("dummy1", "dummy2", "dummy3")
        whenever(dataSource.fetchFavorites()).thenReturn(set)
        whenever(dataSource.insertFavorite(anyString())).thenAnswer { set.add(it.arguments[0] as String) }

        // When
        val testObserver = repo.fetchAllFavorites().test()

        // Then
        assertThat(testObserver.values().size).isEqualTo(1)
        assertThat(testObserver.values().last()).containsExactlyElementsIn(set)

        // When
        val item = "xyz"
        repo.insertFavorite(item)

        // Then
        assertThat(testObserver.values().size).isEqualTo(2)
        assertThat(testObserver.values().last()).containsExactlyElementsIn(set)
        assertThat(testObserver.values().last().last()).isEqualTo(item)
    }

    @Test
    fun `An observer gets notified whenever a new item is deleted`(){
        // Given
        val set = mutableSetOf("dummy1", "dummy2", "dummy3")
        whenever(dataSource.fetchFavorites()).thenReturn(set)
        whenever(dataSource.insertFavorite(anyString())).thenAnswer { set.remove(it.arguments[0] as String) }

        // When
        val testObserver = repo.fetchAllFavorites().test()

        // Then
        assertThat(testObserver.values().size).isEqualTo(1)
        assertThat(testObserver.values().last()).containsExactlyElementsIn(set)

        // When
        val item = "dummy3"
        repo.insertFavorite(item)

        // Then
        assertThat(testObserver.values().size).isEqualTo(2)
        assertThat(testObserver.values().last()).containsExactlyElementsIn(set)
        assertThat(testObserver.values().last().last()).isNotEqualTo(item)
    }
}