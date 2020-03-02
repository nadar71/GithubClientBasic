package com.mapelli.simone.githubclient.repository;


import android.content.Context;

import com.mapelli.simone.githubclient.data.GithubUsersAppRepository;
import com.mapelli.simone.githubclient.data.GithubUsersDatabase;
import com.mapelli.simone.githubclient.data.GithubUsersDbDao;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;
import com.mapelli.simone.githubclient.network.NetworkRequests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


// TODO : to be fixed :
//  launched singularly all pass, altogether only 1 pass; check interdependency
@RunWith(MockitoJUnitRunner.class)
public class GithubAppRepositoryTests {
    @Mock
    GithubUsersDatabase mockDb;
    @Mock
    GithubUsersAppRepository mockRepo;
    @Mock
    GithubUsersDbDao mockDao;
    @Mock
    NetworkRequests  mockNet;
    @Mock
    UserProfile_Mini mockUserProfile_mini;
    @Mock
    LiveData<UserProfile_Full> mockUserProfile_FullLivedata;
    @Mock
    LiveData<List<UserProfile_Mini>>  mockUserListLivedata;
    @Mock
    LiveData<List<UserRepository>>  mockUserRepoListLivedata;
    @Mock
    Context mockApplicationContext;

    GithubUsersAppRepository repository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = GithubUsersAppRepository.getInstance(mockDb);
        when(mockDb.githubUsersDao()).thenReturn(mockDao);
    }

    //----------------------------------------------------------------------------------------------
    //  NETWORK REQUESTS WRAPPING
    //----------------------------------------------------------------------------------------------
    /*
    TODO : to be fixed, when repo invoke void fetch* methods it returns null obviously
    void
    java.lang.NullPointerException
	at com.mapelli.simone.githubclient.data.GithubUsersAppRepository.fetchUserSearch

    @Test
    public void fetchUserSearch_test() throws NullPointerException {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.fetchUserSearch("simo", "1", "50");
        verify(mockNet,times(1)).doUsersSearch("simo",
                "1","50");
    }


    @Test
    public void fetchUserFull_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.fetchUserFull("simo");
        verify(mockNet,times(1)).getUserProfileFullByLogin("simo");
    }

    @Test
    public void fetchUserRepoList_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.fetchUserRepoList("simo","asc");
        verify(mockNet,times(1)).getRepoFilterByName_Direction("simo",
                "asc");
    }

    @Test
    public void fetchRepoFilterByCreated_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.fetchRepoFilterByCreated("simo","asc");
        verify(mockNet,times(1)).getRepoFilterByCreated_Direction("simo",
                "asc");
    }

    @Test
    public void fetchRepoFilterByUpdated_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.fetchRepoFilterByUpdated("simo","asc");
        verify(mockNet,times(1)).getRepoFilterByUpdated_Direction("simo",
                "asc");
    }

    @Test
    public void fetchRepoFilterByPushed_test(String login, String direction) {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.fetchRepoFilterByPushed("simo","asc");
        verify(mockNet,times(1)).getRepoFilterByPushed_Direction("simo",
                "asc");
    }
*/

    //----------------------------------------------------------------------------------------------
    //  QUERY WRAPPING
    //----------------------------------------------------------------------------------------------
    @Test
    public void loadUserList_test(){
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.loadUserList();
        verify(mockDao,times(1)).loadUserList();
        when(mockDao.loadUserList()).thenReturn(mockUserListLivedata);
    }

    @Test
    public void loadUserFull_test(){
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.loadUserFull();
        verify(mockDao,times(1)).loadUserFull();
        when(mockDao.loadUserFull()).thenReturn(mockUserProfile_FullLivedata);
    }

    @Test
    public void loadUserRepoList_test(){
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.loadUserRepoList();
        verify(mockDao,times(1)).loadUserRepoList();
        when(mockDao.loadUserRepoList()).thenReturn(mockUserRepoListLivedata);
    }

    //----------------------------------------------------------------------------------------------
    //  INSERT WRAPPING
    //----------------------------------------------------------------------------------------------

    @Test
    public void insertUserMini_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        UserProfile_Mini user = new UserProfile_Mini("123","nadar71",
                "http://prova.com","simo");
        spy_repository.insertUserMini(user);
        verify(mockDao, times(1)).insertUserMini(user);
    }


    @Test
    public void insertUserFull_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        UserProfile_Full userFull = new UserProfile_Full("tmp","tmp",
                "tmp","tmp","tmp","tmp","tmp");
        spy_repository.insertUserFull(userFull);
        verify(mockDao, times(1)).insertUserFull(userFull);
    }

    @Test
    public void insertUserRepo_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        UserRepository userRepository = new UserRepository("demo","demo",
                "demo","demo","demo","demo",
                "demo","demo","demo");
        spy_repository.insertUserRepo(userRepository);
        verify(mockDao, times(1)).insertUserRepo(userRepository);
    }


    @Test
    public void insertAll_UserMini_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        UserProfile_Mini user = new UserProfile_Mini("123","nadar71",
                "http://prova.com","simo");
        UserProfile_Mini user_01 = new UserProfile_Mini("123","nadar71",
                "http://prova.com","simo");
        List<UserProfile_Mini> listUser = new ArrayList<>();
        listUser.add(user);
        listUser.add(user_01);
        spy_repository.insertAll_UserMini(listUser);
        verify(mockDao, times(1)).insertAll_UserMini(listUser);
    }


    @Test
    public void insertAll_UserRepo_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        UserRepository userRepository = new UserRepository("demo","demo",
                "demo","demo","demo","demo",
                "demo","demo","demo");
        UserRepository userRepository_01 = new UserRepository("demo","demo",
                "demo","demo","demo","demo",
                "demo","demo","demo");
        List<UserRepository> listRepo = new ArrayList<>();
        listRepo.add(userRepository);
        listRepo.add(userRepository_01);
        spy_repository.insertAll_UserRepo(listRepo);
        verify(mockDao, times(1)).insertAll_UserRepo(listRepo);
    }


    //----------------------------------------------------------------------------------------------
    //  DROP TABLE  WRAPPING
    //----------------------------------------------------------------------------------------------

    @Test
    public void dropUserMiniTable_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.dropUserMiniTable();
        verify(mockDao,times(1)).dropUserMiniTable();
    }

    @Test
    public void dropUserFullTable_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.dropUserFullTable();
        verify(mockDao,times(1)).dropUserFullTable();
    }

    @Test
    public void dropUserRepoTable_test() {
        GithubUsersAppRepository spy_repository = spy(repository);
        spy_repository.dropUserRepoTable();
        verify(mockDao,times(1)).dropUserRepoTable();
    }






}
