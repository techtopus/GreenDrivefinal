package com.techtopus.greendrive;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tripfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tripfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tripfragment extends Fragment {
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    */
String start,stop1,stop2,stop3,dest,time,date;

    public tripfragment(String start, String stop1, String stop2, String stop3, String dest, String time, String date) {
        this.start = start;
        this.stop1 = stop1;
        this.stop2 = stop2;
        this.stop3 = stop3;
        this.dest = dest;
        this.time = time;
        this.date = date;
    }

    public tripfragment() {
        // Required empty public constructor
    }

   /* *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tripfragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static tripfragment newInstance(String param1, String param2) {
        tripfragment fragment = new tripfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_tripfragment, container, false);
        TextView t=v.findViewById(R.id.textView50);
        t.setText(start);
        if(stop1!=null) {
            TextView t2 = v.findViewById(R.id.textView59);
            t2.setText(stop1);
        }
        if(stop2!=null) {
            TextView t3 = v.findViewById(R.id.textView63);
            t3.setText(stop2);
        }
        if(stop3!=null) {
            TextView t4 = v.findViewById(R.id.textView66);
            t4.setText(stop3);
        }TextView t5=v.findViewById(R.id.textView58);
        t5.setText(dest);
        TextView t6=v.findViewById(R.id.textView67);
        t6.setText(time);
        TextView t7=v.findViewById(R.id.textView68);
        t7.setText(date);
        return v;
    }
/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
