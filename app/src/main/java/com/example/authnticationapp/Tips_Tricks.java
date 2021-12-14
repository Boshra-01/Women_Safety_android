package com.example.authnticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Tips_Tricks extends AppCompatActivity {


   GridView gridView;
   String[] names = {"def17","def18","def19","def20","def21","def22","def23","def24","def2","def9","def6","def5","def1","def12","def8","def7","def10","def3","def11","def4","def13","def14","def15","def16"};
   int [] images = {R.drawable.def17,R.drawable.def18,R.drawable.def19,R.drawable.def20,R.drawable.def21,R.drawable.def22,R.drawable.def23,R.drawable.def24,R.drawable.def2,R.drawable.def9,R.drawable.def6,R.drawable.def5,R.drawable.def1,R.drawable.def12,R.drawable.def8,R.drawable.def7,R.drawable.def10,R.drawable.def3,R.drawable.def11,R.drawable.def4,R.drawable.def13,R.drawable.def14,R.drawable.def15,R.drawable.def16};

   private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips__tricks);

        button= (Button) findViewById(R.id.button_thingsToRemember);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(),Things_to_remember.class);
            startActivity(intent);
            }
        });

        gridView= findViewById(R.id.gridView);

     CustomAdapter customAdapter = new CustomAdapter(names,images,this);
     gridView.setAdapter(customAdapter);
     gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
             String selectedName = names[i];
             int selectedImage = images[i];
             startActivity(new Intent(Tips_Tricks.this,Tips_tricks_Extended.class).putExtra("names",selectedName).putExtra("images",selectedImage));


         }
     });

    }
    public class  CustomAdapter extends BaseAdapter {

      private String[] imageNames;
      private int[] imagesPhotos;
      private Context context;
        private LayoutInflater layoutInflater;

        public CustomAdapter(String[] imageNames, int[] imagesPhotos, Context context) {
            this.imageNames = imageNames;
            this.imagesPhotos = imagesPhotos;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public int getCount() {
            return imagesPhotos.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

           if (convertView == null){
               convertView = layoutInflater.inflate(R.layout.row_gridview_item,parent,false);

            }
            TextView defense_id = convertView.findViewById(R.id.defense_id) ;
            ImageView imageviewGrid = convertView.findViewById(R.id.imageviewGrid);
            defense_id.setText(imageNames[i]);
            imageviewGrid.setImageResource(imagesPhotos[i]);


            return convertView;
        }
    }
}
