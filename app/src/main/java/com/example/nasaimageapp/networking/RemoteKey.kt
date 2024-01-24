package com.example.nasaimageapp.networking

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(@PrimaryKey val label: String, val nextPage: Int?)