package com.ntukhpi.storozhuk.lab6;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private float firstNumber;
    private float secondNumber;
    private View view;

    private CommonData commonData;

    public SecondFragment() {
    }

    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TextView) getView().findViewById(R.id.errors)).setText("");
        if (commonData.getOperation() != 0) {
            if (commonData.getOperation() == '/') {
                if (secondNumber == 0) {
                    ((TextView) getView().findViewById(R.id.errors)).setText("Second number shouldn't be zero");
                    return;
                }
                commonData.setResult(firstNumber / secondNumber);
            } else {
                commonData.setResult(firstNumber * secondNumber);
            }
            ((TextView) view.findViewById(R.id.operation))
                    .setText(String.format("%s %c %s = %s", firstNumber, commonData.getOperation(), secondNumber, commonData.getResult()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }

    public CommonData getCommonData() {
        return commonData;
    }

    public void setCommonData(CommonData commonData) {
        this.commonData = commonData;
    }

    public void setNumbers(float firstNumber, float secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }
}