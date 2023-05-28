package guru.qa.niffler.api;

import guru.qa.niffler.model.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/updateUserInfo")
    Call<UserJson> updateUserInfo(@Body UserJson spend);
}
