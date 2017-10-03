package com.example.santiagolopezgarcia.talleres.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.santiagolopezgarcia.talleres.view.interfaces.IListaOriginal;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class SearchableSpinner extends Spinner implements View.OnTouchListener,
        SearchableListDialog.SearchableItem {

    private Context _context;
    private List _items;
    private SearchableListDialog _searchableListDialog;
    ArrayAdapter adapter;

    public SearchableSpinner(Context context) {
        super(context);
        this._context = context;
        init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        init();
    }

    public SearchableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this._context = context;
        init();
    }

    public void setAdapter(ArrayAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
        _searchableListDialog.setAdapter(adapter);
    }

    private void init() {
        _searchableListDialog = SearchableListDialog.newInstance(_items);
        _searchableListDialog.setOnSearchableItemClickListener(this);
        setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (null != _searchableListDialog.getListAdapter()) {
                if (_searchableListDialog.getListAdapter() instanceof SearchableSpinnerAdapterResize) {
                    ((SearchableSpinnerAdapterResize) _searchableListDialog.getListAdapter()).ajustarTamaño(false);
                    _searchableListDialog.setPosicionActiva(((SearchableSpinnerAdapterResize) _searchableListDialog.getListAdapter())
                            .traerPosicionActiva());
                }
                if (_searchableListDialog.getListAdapter() instanceof SearchableSpinnerAdapterText) {
                    ((SearchableSpinnerAdapterText) _searchableListDialog.getListAdapter()).ajustarTexto(false);
                }
                if (!_searchableListDialog.isAdded())
                    _searchableListDialog.show(scanForActivity(_context).getFragmentManager(), "TAG");
            }
        }
        return true;
    }

    private static Activity scanForActivity(Context context) {
        if (context == null)
            return null;
        else if (context instanceof Activity)
            return (Activity) context;
        else if (context instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) context).getBaseContext());

        return null;

    }

    @Override
    public void onSearchableItemClicked(Object item, int position) {
        ((SearchableSpinnerAdapterResize) _searchableListDialog.getListAdapter()).ajustarTamaño(true);
        if (_searchableListDialog.getListAdapter() instanceof SearchableSpinnerAdapterText) {
            ((SearchableSpinnerAdapterText) _searchableListDialog.getListAdapter()).ajustarTexto(true);
        }
        if (_searchableListDialog.getListAdapter() instanceof IListaOriginal) {
            setSelection(((IListaOriginal) _searchableListDialog.getListAdapter()).traerListaOriginal().indexOf(item));
        }
//        setSelection(position);
    }

    @Override
    public void onClose() {
        ((SearchableSpinnerAdapterResize) _searchableListDialog.getListAdapter()).ajustarTamaño(true);
        if (_searchableListDialog.getListAdapter() instanceof SearchableSpinnerAdapterText) {
            ((SearchableSpinnerAdapterText) _searchableListDialog.getListAdapter()).ajustarTexto(true);
        }
    }

    public void setTitle(String strTitle) {
        _searchableListDialog.setTitle(strTitle);
    }

    public interface SearchableSpinnerAdapterResize {

        void ajustarTamaño(boolean ajustar);

        int traerPosicionActiva();
    }

    public interface SearchableSpinnerAdapterText {

        void ajustarTexto(boolean ajustar);
    }
}