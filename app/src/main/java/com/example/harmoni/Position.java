package com.example.harmoni;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

// à l'inscription lorsqu'on arrive à l'interface de localisation
// on clique sur le bouton  de permission qui permet d'obtenir une autorisation permanente
// une seule fois et capturer la localisation de l'utilisateur à chaque ouverture de l'application
public class Position extends AppCompatActivity {
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        // API Google Play Services pour récupérer la localisation de l'utilisateur.
        private FusedLocationProviderClient fusedLocationClient;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_position);

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            Button button = findViewById(R.id.button9);
            Button skip =findViewById(R.id.button7);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //il vérifie si la permission d'accès à la localisation est déjà accordée ou non
                    // Demande de permission
                    if (ActivityCompat.checkSelfPermission(Position.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Position.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                    } else {
                        // Permission déjà accordée
                        getCurrentLocation();
                    }
                    //Ajouter le passage à l'interface d'insertion de music pour ce boutton ,
                    // il faut faire la méme chose pour skip
                }
            });
        }

        private void getCurrentLocation() {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Localisation actuelle capturée avec succès
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                //Un message Toast est une notification qui s'affiche brièvement sur l'écran d'un appareil Android.
                                Toast.makeText(Position.this, "Latitude: " + latitude + " Longitude: " + longitude, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        //La méthode onRequestPermissionsResult() est appelée lorsque l'utilisateur
        // donne ou refuse la permission d'accès à la localisation. Si la permission
        // est accordée, il récupère la localisation actuelle en appelant la méthode
        // getCurrentLocation(). Si la permission est refusée, un message d'erreur est affiché.
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission accordée
                    getCurrentLocation();
                } else {
                    // Permission refusée
                    Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }