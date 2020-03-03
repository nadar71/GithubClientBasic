package com.mapelli.simone.githubclient.UI.db;


import com.mapelli.simone.githubclient.data.DateConverter;
import com.mapelli.simone.githubclient.data.GithubUsersDatabase;
import com.mapelli.simone.githubclient.data.GithubUsersDbDao;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@MediumTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class GithubDbDaoTests {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private GithubUsersDatabase githubUsersDatabase;
    private GithubUsersDbDao githubUsersDbDao;


    @Before
    public void createDb() {
        githubUsersDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(),
                GithubUsersDatabase.class).build();
        githubUsersDbDao = githubUsersDatabase.githubUsersDao();

    }

    @After
    public void closeDb() throws IOException {
        githubUsersDatabase.close();
    }

    //----------------------------------------------------------------------------------------------
    //  QUERY
    //----------------------------------------------------------------------------------------------
    @Test
    public void loadUserList_test(){
        githubUsersDbDao.insertUserMini(createUserMini());
        githubUsersDbDao.insertUserMini(createUserMini());
        LiveData<List<UserProfile_Mini>> userMiniList = githubUsersDbDao.loadUserList();

        Observer<List<UserProfile_Mini>> observer = users -> assertEquals(2,users.size());
        userMiniList.observeForever(observer);

        List<UserProfile_Mini> currentUsers = userMiniList.getValue();
        assertEquals(currentUsers.size(),2);
    }



    @Test
    public void loadUserFull_test(){
        UserProfile_Full userNew = new UserProfile_Full("nadar71","1234",
                "http://prova.it/img.png", "http://prova.it/repo.html",
                "simo","bg","prova@mail.it");
        githubUsersDbDao.insertUserFull(userNew);
        LiveData<UserProfile_Full> userProfileFull = githubUsersDbDao.loadUserFull();

        Observer<UserProfile_Full> observer = user -> assertEquals(userProfileFull.getValue().getId(),
                                                        user.getId());
        userProfileFull.observeForever(observer);

        UserProfile_Full currentUser = userProfileFull.getValue();
        assertEquals(currentUser.getId(),userNew.getId());
    }



    @Test
    public void loadUserRepoList_test(){
        githubUsersDbDao.insertUserRepo(createUserRepo());
        githubUsersDbDao.insertUserRepo(createUserRepo());
        LiveData<List<UserRepository>> useRepositoryList = githubUsersDbDao.loadUserRepoList();

        Observer<List<UserRepository>> observer = repos -> assertEquals(2,repos.size());
        useRepositoryList.observeForever(observer);

        List<UserRepository> currentRepos = useRepositoryList.getValue();
        assertEquals(currentRepos.size(),2);
    }



    //----------------------------------------------------------------------------------------------
    //  INSERT
    //----------------------------------------------------------------------------------------------

    /*already tested in previous :
        void insertUserMini(UserProfile_Mini userProfile_Mini);
        void insertUserFull(UserProfile_Full userProfile_Full);
        void insertUserRepo(UserRepository userRepository);
     */


    @Test
    public void insertAll_UserMini_test(){
        List<UserProfile_Mini> src = new ArrayList<>();
        LiveData<List<UserProfile_Mini>> dst;
        src.add(createUserMini());
        src.add(createUserMini());
        githubUsersDbDao.insertAll_UserMini(src);
        dst = githubUsersDbDao.loadUserList();

        Observer<List<UserProfile_Mini>> observer = users -> assertEquals(2,users.size());
        dst.observeForever(observer);

        List<UserProfile_Mini> currentUsers = dst.getValue();
        assertEquals(currentUsers.size(),2);


    }


    @Test
    public void insertAll_UserRepo_test(){
        List<UserRepository> src = new ArrayList<>();
        LiveData<List<UserRepository>> dst;
        src.add(createUserRepo());
        src.add(createUserRepo());
        githubUsersDbDao.insertAll_UserRepo(src);
        dst = githubUsersDbDao.loadUserRepoList();

        Observer<List<UserRepository>> observer = users -> assertEquals(2,users.size());
        dst.observeForever(observer);

        List<UserRepository> currentUsers = dst.getValue();
        assertEquals(currentUsers.size(),2);
    }




    public UserProfile_Mini createUserMini(){
        UserProfile_Mini user = new UserProfile_Mini("1234","nadar71",
                "http://prova.it","simo");
        return user;
    }


    public UserRepository createUserRepo(){
        Date today = new Date();
        Long today_long = DateConverter.INSTANCE.fromDate(today);
        UserRepository user = new UserRepository("1234","simo","simo mape",
                 "http://prova.it/repo.html", "today_long",
                "today_long", "today_long", "3","2");
        return user;
    }



}
