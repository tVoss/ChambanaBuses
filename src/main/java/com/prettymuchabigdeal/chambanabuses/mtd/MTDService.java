package com.prettymuchabigdeal.chambanabuses.mtd;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prettymuchabigdeal.chambanabuses.mtd.response.DepartureResponse;
import com.prettymuchabigdeal.chambanabuses.mtd.response.RouteResponse;
import com.prettymuchabigdeal.chambanabuses.mtd.response.StopResponse;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by tyler on 1/31/15.
 */
public interface MTDService {

    String API_KEY = "97e3ffb44cbb439b83dccdbfe7444a27";

    String VERSION = "v2.2";
    String BASE_URL = "https://developer.cumtd.com/api/" + VERSION + "/json";

    @GET("/GetDeparturesByStop")
    void getDeparturesByStop(@Query("stop_id") String stopId,
                                    @Query("route_id") String routeId,
                                    @Query("pt") int pt,
                                    Callback<DepartureResponse> cb);

    @GET("/GetRoute")
    void getRoute(@Query("route_id") String routeId,
                         Callback<RouteResponse> cb);

    @GET("/GetRoutes")
    void getRoute(Callback<RouteResponse> cb);

    @GET("/GetRoutesByStop")
    void getRouteByStop(@Query("stop_id") String stopId,
                               Callback<RouteResponse> cb);

    @GET("/GetStop")
    void getStop(@Query("stop_id") String StopId, Callback<StopResponse> cb);

    @GET("/GetStops")
    void getStops(Callback<StopResponse> cb);

    @GET("/GetStopsByLatLon")
    void getStopsByLatLon(@Query("lat") float lat,
                                 @Query("lon") float lon,
                                 @Query("count") int count,
                                 Callback<StopResponse> cb);

    @GET("/GetStopsBySearch")
    void getStopsBySearch(@Query("query") String query,
                                 @Query("count") int count,
                                 Callback<StopResponse> cb);

    class Helper implements RequestInterceptor {

        private Helper() {
        }

        public static MTDService create() {
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL)
                    .setConverter(new GsonConverter(gson))
                    .setRequestInterceptor(new Helper())
                    .build();

            return restAdapter.create(MTDService.class);
        }

        @Override
        public void intercept(RequestFacade request) {
            request.addQueryParam("key", API_KEY);
            //TODO: implement this: request.addQueryParam("changeset_id", null);
        }
    }
}
