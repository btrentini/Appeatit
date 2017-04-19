package it.Services;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import it.Model.Meal;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tobias on 28/03/2017.
 */

public class ServicesImpl {

    public ServicesImpl(){
        Log.i("INFO","ServiceImpl()");
        Call<List<Meal>> call = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://appeatit.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServices iServices = retrofit.create(IServices.class);
        call = iServices.getMeals();

        try {
            List<Meal> meals = call.execute().body();
            Log.i("INFO","Lista Meals -> "+meals.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
