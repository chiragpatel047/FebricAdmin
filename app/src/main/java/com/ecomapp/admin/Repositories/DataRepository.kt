package com.ecomapp.admin.Repositories

import android.net.Uri
import com.ecomapp.admin.Models.BannerModel
import com.ecomapp.admin.Models.MainCatModel
import com.ecomapp.admin.Models.SubCatModel
import com.ecomapp.febric.Models.ProuctModel
import com.ecomapp.febric.Repositories.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DataRepository @Inject constructor(val database : FirebaseFirestore,val storage: FirebaseStorage) {

    var bannerList  = ArrayList<BannerModel>()
    var mainCatList = ArrayList<MainCatModel>()
    var subCatList = ArrayList<SubCatModel>()
    var AllProductList = ArrayList<ProuctModel>()


    suspend fun LoadHomeBanners() : Response<ArrayList<BannerModel>>{

        val snapshot = withContext(Dispatchers.IO){
            database.collection("HomeBanners").get().await()
        }

        val fetching = withContext(Dispatchers.IO){
            bannerList.addAll(snapshot.toObjects(BannerModel::class.java))
        }

        return try {
            Response.Sucess(bannerList)
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }
    }

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

    suspend fun AddMainCategory(parentCat : String,mainCatModel: MainCatModel) :  Response<String>{

        val upload = withContext(Dispatchers.IO){
            val ref =  storage.reference.child("Categories")
                .child(parentCat).child(mainCatModel.mainCatName!!)
            ref.putFile(Uri.parse(mainCatModel.mainCatImage)).await()
            ref.downloadUrl.await()
        }

        val insert = withContext(Dispatchers.IO){
            mainCatModel.mainCatImage = upload.toString()
            database.collection("Categories")
                .document(parentCat)
                .collection("MainCategories")
                .document(mainCatModel.mainCatName!!).set(mainCatModel).await()
        }

        return try {
            Response.Sucess("Success")
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }
    }

    suspend fun LoadSubCatigories(parentCatName : String,mainCatName : String) : Response<ArrayList<SubCatModel>>{

        val snapshot = withContext(Dispatchers.IO){
            database.collection("Categories")
                .document(parentCatName)
                .collection("MainCategories")
                .document(mainCatName)
                .collection("SubCategories").get().await()
        }

        val fetching = withContext(Dispatchers.IO){
            subCatList.addAll(snapshot.toObjects(SubCatModel::class.java))
        }

        return try {
            Response.Sucess(subCatList)
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }
    }

    suspend fun AddSubCategory(parentCatName : String,mainCatName : String,subCatModel: SubCatModel) :  Response<String>{

        val insert = withContext(Dispatchers.IO){

            database.collection("Categories")
                .document(parentCatName)
                .collection("MainCategories")
                .document(mainCatName)
                .collection("SubCategories")
                .document(subCatModel.subCatName!!)
                .set(subCatModel).await()
        }

        return try {
            Response.Sucess("Success")
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }
    }

    suspend fun LoadAllProducts() : Response<ArrayList<ProuctModel>>{

        val snapshot = withContext(Dispatchers.IO){
            database.collection("AllProducts").get().await()
        }

        val fetching = withContext(Dispatchers.IO){
            AllProductList.addAll(snapshot.toObjects(ProuctModel::class.java))
        }

        return try {
            Response.Sucess(AllProductList)
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }
    }

    suspend fun AddNewProduct(parentCatName : String,mainCatName : String,prouctModel: ProuctModel) :  Response<String>{

        val addIntoAllProduct = withContext(Dispatchers.IO){
            database.collection("AllProducts")
                .document(prouctModel.ProductId!!)
                .set(prouctModel)
                .await()
        }

        val insertIntoCat = withContext(Dispatchers.IO){

//            database.collection("Categories")
//                .document(parentCatName)
//                .collection("MainCategories")
//                .document(mainCatName)
//                .collection("SubCategories")
//                .document(subCatModel.subCatName!!)
//                .set(subCatModel).await()
        }

        return try {
            Response.Sucess("Success")
        }catch (e : Exception){
            Response.Error(e.message.toString())
        }
    }



}