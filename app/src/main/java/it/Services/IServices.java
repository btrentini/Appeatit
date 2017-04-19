package it.Services;

import java.util.List;

import it.Model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tobias on 28/03/2017.
 */

public interface IServices {

    @GET("Meals")
    Call<List<Meal>> getMeals();

}
