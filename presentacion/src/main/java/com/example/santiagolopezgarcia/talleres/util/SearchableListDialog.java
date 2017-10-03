package com.example.santiagolopezgarcia.talleres.util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.santiagolopezgarcia.talleres.R;
import com.example.santiagolopezgarcia.talleres.view.interfaces.IFiltro;

import java.io.Serializable;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 10/2/17.
 */

public class SearchableListDialog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String ITEMS = "items";

    private ArrayAdapter listAdapter;
    private ListView _listViewItems;
    private SearchableItem _searchableItem;
    private SearchView _searchView;
    private String _strTitle;
    private Button btnCerrar;
    private int posicionActiva;
    private List items;

    public SearchableListDialog() {

    }

    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new
                SearchableListDialog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        multiSelectExpandableFragment.setArguments(args);

        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = (List) getArguments().getSerializable(ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        ((IFiltro) _listViewItems.getAdapter()).filtrar("");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View rootView = inflater.inflate(R.layout.searchable_list_dialog, null);
        setData(rootView);

        btnCerrar = (Button) rootView.findViewById(R.id.btnCerrarDialog);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strTitle = _strTitle == null ? "Elemento aforo" : _strTitle;

        View viewtitulo = inflater.inflate(R.layout.custom_titulo_spinner, null);
        TextView tvTitulo = (TextView) viewtitulo.findViewById(R.id.tvTituloSpinner);
        tvTitulo.setText(strTitle);
        alertDialog.setCustomTitle(viewtitulo);

        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnCerrar.setOnClickListener(v -> {
            ocultarTeclado();
            _searchableItem.onClose();
            dialog.dismiss();
        });
        return dialog;
    }

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setPosicionActiva(int posicion){
        posicionActiva = posicion;
        if(_listViewItems!=null)
            _listViewItems.setVerticalScrollbarPosition(posicionActiva);
    }

    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }

    private void setData(View rootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        _searchView = (SearchView) rootView.findViewById(R.id.search);
        _searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName
                ()));
        _searchView.setIconifiedByDefault(false);
        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);
        _listViewItems = (ListView) rootView.findViewById(R.id.listItems);
        _listViewItems.setAdapter(listAdapter);
        _listViewItems.setTextFilterEnabled(true);
        _listViewItems.setSelection(posicionActiva);
        _listViewItems.setOnItemClickListener((parent, view, position, id) -> {
            _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
            onQueryTextChange("");
            ocultarTeclado();
            getDialog().dismiss();


        });
    }

    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager)getDialog().getCurrentFocus().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getDialog().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        _searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (_listViewItems.getAdapter() instanceof IFiltro) {
            ((IFiltro) _listViewItems.getAdapter()).filtrar(s);
        }
        return true;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.listAdapter = adapter;
    }

    public ArrayAdapter getListAdapter() {
        return listAdapter;
    }

    public interface SearchableItem<T> {
        void onSearchableItemClicked(T item, int position);

        void onClose();
    }

}