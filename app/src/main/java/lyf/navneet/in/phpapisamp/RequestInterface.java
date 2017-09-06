package lyf.navneet.in.phpapisamp;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lyf.navneet.in.phpapisamp.model.OuterModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Sree on 16-12-2016.
 */
public interface RequestInterface {

    @Headers("Content-Type: application/json")
    @POST("register.php")
    Call<OuterModel> getPOJO(@Body Map<String,String> jsonObject);

    @Headers("Content-Type: application/json")
    @POST("login.php")
    Call<OuterModel> getPOJOLogin(@Body Map<String,String> jsonObject);
}
