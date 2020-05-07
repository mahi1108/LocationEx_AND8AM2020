package cubex.mahesh.locationex_and8am2020

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val status = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        if(status== PackageManager.PERMISSION_GRANTED){
            getLocation()
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION),
                    11)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun  getLocation()
    {
     var lManager:LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager

        lManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000.toLong(),1.toFloat(),object:LocationListener{
            override fun onLocationChanged(location: Location?) {
                textView.setText("Latitude : "+ location?.latitude)
                textView2.setText("Longitude : "+ location?.longitude)
                //lManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }
        }
        )
    }
    var count = 0;
    fun notify(view: View) {
        ++count;
        var nManager:NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        var n:NotificationCompat.Builder? = null
        if(android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O){
                var nChannel:NotificationChannel =
                        NotificationChannel(count.toString(),
                                "and8am2020",
                                NotificationManager.IMPORTANCE_HIGH)
                nChannel.setShowBadge(true)
                nManager.createNotificationChannel(nChannel)
            n = NotificationCompat.Builder(this,
                    "and8am2020")
        }else{
            n = NotificationCompat.Builder(this)
        }
        n.setSmallIcon(R.drawable.ic_directions_bike_black_24dp)
        n.setTicker("Welcome to NareshIT..")
        n.setContentTitle("NareshIT")
        n.setContentText("this is sample notification from And8AM2020..")
        var intent = Intent(this,MainActivity::class.java)
        var pIntent = PendingIntent.getActivity(this,
                0,intent,0)
        n.setContentIntent(pIntent)
        n.setAutoCancel(true)
        n.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        nManager.notify(count,n.build())
    }
}
