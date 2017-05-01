package it.appeatit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import it.Adapters.MenuAdapter;
import it.Base.BaseActivity;
import it.DAO.CustomArrayRequest;
import it.DAO.CustomObjectRequest;
import it.Model.Address;
import it.Model.DailyMeal;
import it.Model.Meal;
import it.Model.User;


public class MainActivity extends BaseActivity {


    public final String DAILY_MEALS = "http://appeatit.life/DailyMeals";
    private List<DailyMeal> dailyMealList = new ArrayList<>();
    private RecyclerView rv;
    private MenuAdapter adapter;

    private String mealName;
    private double mealPrice;
    private String mealPhoto;
    private String starRating;
    private int maxPeople;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar(((Toolbar)findViewById(R.id.toolbar)));
        setupNavigationDrawer(this);

        adapter = new MenuAdapter(dailyMealList, this);

        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        RequestQueue rq = Volley.newRequestQueue(this);

        CustomArrayRequest request = new CustomArrayRequest(
                Request.Method.GET,
                DAILY_MEALS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++){

                                JSONObject jo = response.getJSONObject(i);

                                DailyMeal dailyMeal = new DailyMeal();

                                dailyMeal.setId(jo.getInt("id"));
                                dailyMeal.setDate(new Date());//TODO: Arrumar data.

                                JSONObject joMeal = jo.getJSONObject("Meal");

                                Meal meal = new Meal();

                                    mealName = joMeal.getString("name");
                                    meal.setName(mealName);

                                    mealPrice = (float)joMeal.getDouble("price");
                                    meal.setPrice((float)mealPrice);

                                    mealPhoto = URLDecoder.decode(joMeal.getString("url_image"), "UTF-8");
                                    meal.setPhoto(mealPhoto);

                                    starRating = joMeal.getString("star_rating");
                                    meal.setRating(starRating);

                                    maxPeople = joMeal.getInt("max_people");
                                    meal.setMaxPeople(maxPeople);


                                JSONObject joChef = joMeal.getJSONObject("User");
                                    User chef = new User();
                                    chef.setName(joChef.getString("name"));
                                    chef.setId(joChef.getInt("id"));
                                    chef.setEmail(joChef.getString("email"));
                                    meal.setChef(chef);
                                    dailyMeal.setMeal(meal);

                                JSONObject joAddress = jo.getJSONObject("Address");

                                Address address = new Address();
                                    address.setId(joAddress.getInt("id"));
                                    address.setStreet(joAddress.getString("street"));
                                    address.setNeighborhood(joAddress.getString("neighborhood"));
                                    chef.getAddressList().add(address);
                                    dailyMeal.setAddress(address);

                                dailyMealList.add(dailyMeal);

                            }
                        }catch(JSONException e){
                            Toast.makeText(MainActivity.this, "Error  jsonParse -> "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                           Toast.makeText(MainActivity.this, "Error decoding image -> "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        rv.setAdapter(adapter);
                    }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("INFO",error.getMessage());
                    }
                }

        );
        request.setTag("teste");
        rq.add(request);





    }


}
