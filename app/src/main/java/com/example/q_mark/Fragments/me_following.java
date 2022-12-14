package com.example.q_mark.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q_mark.Adapter.follower_adapter;
import com.example.q_mark.Model.User;
import com.example.q_mark.R;
import com.example.q_mark.databinding.MeFollowingBinding;
import com.example.q_mark.databinding.MyFriendsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class me_following extends Fragment {

    ArrayList<User> list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mauth;
    MeFollowingBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.me_following,container,false);

        binding= MeFollowingBinding.inflate(inflater,container,false);
        follower_adapter ff= new follower_adapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setAdapter(ff);

        String uid=FirebaseAuth.getInstance().getUid();

        firebaseDatabase.getReference().child("User").child(uid).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {


//                        User user=dataSnapshot.getValue(User.class);
//                        user.setUid(dataSnapshot.getKey());
//                        System.out.println(dataSnapshot.getKey());
//                        list.add(user);
                    firebaseDatabase.getReference().child("User").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user=snapshot.getValue(User.class);
                            user.setUid(snapshot.getKey());
//                            System.out.println(snapshot.getKey());
//                            System.out.println(user.getName());
                            list.add(user);
                            ff.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                ff.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }
}