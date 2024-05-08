package com.eep.android.gestionifema.data

import com.eep.android.gestionifema.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepositoy {

fun getAllItemsStream(): Flow<List<User>>


fun getItemStream(id: Int): Flow<User?>

/**
 * Insert item in the data source
 */
suspend fun insertItem(item: User)

/**
 * Delete item from the data source
 */
suspend fun deleteItem(item: User)

/**
 * Update item in the data source
 */
suspend fun updateItem(item: User)
}