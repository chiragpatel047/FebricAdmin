package com.ecomapp.admin.Repositories

import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.febric.Repositories.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DataRepository @Inject constructor(val database : FirebaseFirestore,val storage: FirebaseStorage) {

    var mainCatList = ArrayList<MainCatModel>()

    suspend fun LoadMainCategories(catName : String) : Response<ArrayList<MainCatModel>>{

        val snapshot = withContext(Dispatchers.IO){
            database.collection("Categories")
                .document(catName)
                .collection("MainCategories")
                .get().await()
        }

        val fetching = withContext(Dispatchers.IO){
            mainCatList.addAll(snapshot.toObjects(MainCatModel::class.java))
        }

        return try {
            Response.Sucess(mainCatList)
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }

    }

    suspend fun AddMainCategory(parentCat : String,mainCatModel: MainCatModel){
        val insert = withContext(Dispatchers.IO) {
            database.collection("Categories")
                .document(parentCat)
                .collection("MainCategories")
                .document(mainCatModel.mainCatName!!).set(mainCatModel).await()
        }
    }
}