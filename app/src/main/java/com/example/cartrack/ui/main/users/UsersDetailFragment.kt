package com.example.cartrack.ui.main.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cartrack.R
import com.example.cartrack.model.UsersResponse
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class UsersDetailFragment : Fragment() {

    private var mMap: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Configuration.getInstance()
            .load(
                requireActivity().applicationContext,
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
            )
        return inflater.inflate(R.layout.fragment_users_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMap = view.findViewById(R.id.map)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments.let {
            val position = it?.getParcelable<UsersResponse>("userData")
            createMarker(position)
        }
    }

    private fun createMarker(position: UsersResponse?) {

        val mapController: IMapController = mMap!!.controller
        mapController.setZoom(6.0)

        val startPoint = GeoPoint(position!!.address.geo.lat.toDouble()
                                    ,position.address.geo.lng.toDouble())
        mapController.setCenter(startPoint)

        val startMarker = Marker(mMap)
        startMarker.position = startPoint
        startMarker.title = position.name
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mMap?.overlays?.add(startMarker)
    }

    override fun onResume() {
        super.onResume()
        mMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMap?.onPause()
    }
}