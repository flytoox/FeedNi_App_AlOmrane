package com.example.myloginapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myloginapp.Model.Post;
import com.example.myloginapp.Model.Users;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
// had lclass dyal kifach t fetchi l post la wrk 3lih l user fl map
public class onepost {
   public static void poost(Context con, String post_id, View mView, LatLng local_marker){
       String city = "",addr = null;
       Geocoder geoCoder = new Geocoder(con, Locale.getDefault());
       double lat = local_marker.latitude;
       double lng = local_marker.longitude;
       try {
           List<Address> address = geoCoder.getFromLocation(lat,lng,1);
             city= address.get(0).getLocality();
            addr = address.get(0).getAddressLine(0);

       } catch (IOException e) {}
       catch (NullPointerException e) {}


     FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    ImageView postPic , commentsPic , likePic;
    CircleImageView profilePic ;
    TextView postUsername , postDate , postCaption , postLikes,City,Type,address;
    likePic = mView.findViewById(R.id.like_btn);
    commentsPic = mView.findViewById(R.id.comments_post);
    postLikes = mView.findViewById(R.id.like_count_tv);
    postPic = mView.findViewById(R.id.user_post);
    profilePic = mView.findViewById(R.id.profile_pic);
    postUsername = mView.findViewById(R.id.username_tv);
    postDate = mView.findViewById(R.id.date_tv);
    postCaption = mView.findViewById(R.id.caption_tv);
    Type=mView.findViewById(R.id.Type);
       address=mView.findViewById(R.id.address);
    BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(con,R.style.BottomSheetDialogTheme);
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String currentUserId = firebaseAuth.getCurrentUser().getUid();
        if(mView.getParent() != null) {
        ((ViewGroup)mView.getParent()).removeView(mView); // <- fix
    }


    Task<DocumentSnapshot> pos_t = db.collection("Posts").document(post_id).get();


       String finalAddr = addr;
       pos_t.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()){

                Post pos_t = task.getResult().toObject(Post.class);

                String postUserId = pos_t.getUser();
                firestore.collection("Users").document(postUserId).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    Users users = task.getResult().toObject(Users.class);
                                    Glide.with(con).load(pos_t.getImage()).into(postPic);
                                    Glide.with(con).load(users.getImage()).into(profilePic);
                                    postUsername.setText(users.getName());
                                    postDate.setText(pos_t.getTime().toString());
                                    postCaption.setText(pos_t.getCaption());
                                    Type.setText(pos_t.getType());
                                    address.setText(finalAddr);
                                } else {
                                    Toast.makeText(con, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } }});




        likePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("Posts/" + post_id + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()){
                            Map<String , Object> likesMap = new HashMap<>();
                            likesMap.put("timestamp" , FieldValue.serverTimestamp());
                            firestore.collection("Posts/" + post_id + "/Likes").document(currentUserId).set(likesMap);
                        }else{
                            firestore.collection("Posts/" + post_id + "/Likes").document(currentUserId).delete();
                        }
                    }
                });
            }
        });

        //like color change
        firestore.collection("Posts/" + post_id + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    if (value.exists()){
                        likePic.setImageDrawable(con.getDrawable(R.drawable.after_liked));
                    }else{
                        likePic.setImageDrawable(con.getDrawable(R.drawable.before_liked));
                    }
                }
            }
        });

        //likes count
        firestore.collection("Posts/" + post_id + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    if (!value.isEmpty()){
                        int count = value.size();
                        postLikes.setText(count + " Likes");
                    }else{
                        postLikes.setText(0+ " Likes");
                    }
                }
            }
        });

       postPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(con , CommentsActivity.class);
                commentIntent.putExtra("postid" , post_id);
                con.startActivity(commentIntent);
            }
        });
        //comments implementation
        commentsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(con , CommentsActivity.class);
                commentIntent.putExtra("postid" , post_id);
                con.startActivity(commentIntent);
            }
        });
       bottomSheetDialog.setContentView(mView);
       bottomSheetDialog.show();

    }
}
