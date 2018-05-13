package com.abelsuviri.data.network;

import com.abelsuviri.data.model.Road;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Abel Suviri
 */

public interface IRoadService {
    @GET("Road/{road_id}")
    Observable<List<Road>> getRoads(
            @Path("road_id") String id,
            @Query("app_id") String appId,
            @Query("app_key") String appKey);
}
