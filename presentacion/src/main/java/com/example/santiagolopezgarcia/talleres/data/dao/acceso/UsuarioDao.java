package com.example.santiagolopezgarcia.talleres.data.dao.acceso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dominio.modelonegocio.Usuario;
import com.example.dominio.administracion.UsuarioRepositorio;
import com.example.santiagolopezgarcia.talleres.data.dao.DaoBase;
import com.example.santiagolopezgarcia.talleres.data.dao.correria.ContratoDao;
import com.example.utilidades.helpers.DateHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

public class UsuarioDao extends DaoBase implements UsuarioRepositorio {


    static final String NombreTabla = "sirius_usuario";

    public UsuarioDao(Context context) {
        super(context);
        this.operadorDatos.SetNombreTabla(NombreTabla);
    }

    @Override
    public Usuario obtener(String codigo) throws ParseException {
        Usuario usuario = new Usuario();
        Cursor cursorUsuarios = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " WHERE " + ColumnaUsuario.CODIGOUSUARIO
                + " = '" + codigo + "'");
        if (cursorUsuarios.moveToFirst()) {
            usuario = convertirCursorAObjeto(cursorUsuarios);
        }
        cursorUsuarios.close();
        return usuario;
    }

    @Override
    public List<Usuario> cargar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursorUsuarios = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + " ORDER BY " + ColumnaUsuario.CODIGOUSUARIO);
        if (cursorUsuarios.moveToFirst()) {
            while (!cursorUsuarios.isAfterLast()) {
                try {
                    listaUsuarios.add(convertirCursorAObjeto(cursorUsuarios));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cursorUsuarios.moveToNext();
            }
        }
        cursorUsuarios.close();
        return listaUsuarios;
    }

    @Override
    public List<Usuario> cargarXContrato(String codigoContrato) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Cursor cursorUsuarios = this.operadorDatos.cargar("SELECT * FROM " + NombreTabla + "" +
                " WHERE " + ColumnaUsuario.CODIGOCONTRATO + " = '" + codigoContrato + "'" +
                " ORDER BY " + ColumnaUsuario.CODIGOUSUARIO);
        if (cursorUsuarios.moveToFirst()) {
            while (!cursorUsuarios.isAfterLast()) {
                try {
                    listaUsuarios.add(convertirCursorAObjeto(cursorUsuarios));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cursorUsuarios.moveToNext();
            }
        }
        cursorUsuarios.close();
        return listaUsuarios;
    }

    @Override
    public boolean modificarDespuesDeCarga() {
        int resultado =
                this.operadorDatos.borrar(ColumnaUsuario.CODIGOUSUARIOINGRESO + " IS NULL OR "
                                + ColumnaUsuario.CODIGOUSUARIOINGRESO + " = '' "
                        , new String[]{});

        ContentValues contentValues = new ContentValues();
        String valorNulo = null;
        contentValues.put(ColumnaUsuario.CODIGOUSUARIOINGRESO, valorNulo);

        this.operadorDatos.actualizar(contentValues, ColumnaUsuario.CODIGOUSUARIOINGRESO + " ='Nuevo'");

        return resultado > 0;
    }

    @Override
    public boolean guardar(Usuario usuario) throws ParseException {
        ContentValues datos = convertirObjetoAContentValues(usuario);
        this.operadorDatos.insertar(datos);
        return true;
    }

    @Override
    public boolean eliminar(Usuario usuario) {
        try {
            ContentValues datos = convertirObjetoAContentValues(usuario);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.borrar(ColumnaUsuario.CODIGOUSUARIO + " = ? "
                , new String[]{usuario.getCodigoUsuario()}) > 0;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        ContentValues contentUsuarios = null;
        try {
            contentUsuarios = convertirObjetoAContentValues(usuario);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this.operadorDatos.actualizar(contentUsuarios, ColumnaUsuario.CODIGOUSUARIO + " = '" + usuario.getCodigoUsuario() + "'");
    }

    @Override
    public boolean guardar(List<Usuario> listaUsuarios) throws ParseException {
        List<ContentValues> listaContentValues = new ArrayList<>(listaUsuarios.size());
        for (Usuario usuario : listaUsuarios) {
            listaContentValues.add(convertirObjetoAContentValues(usuario));
        }
        boolean resultado = this.operadorDatos.insertarConTransaccion(listaContentValues);
        listaContentValues.clear();
        return resultado;
    }

    private Usuario convertirCursorAObjeto(Cursor cursor) throws ParseException {
        Usuario usuario = new Usuario();
        usuario.setCodigoUsuario(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CODIGOUSUARIO)));
        usuario.getPerfil().setCodigoPerfil(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CODIGOPERFIL)));
        usuario.setNombre(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.NOMBRE)));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaUsuario.CODIGOCONTRATO))) {
            usuario.getContrato().setCodigoContrato(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CODIGOCONTRATO)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaUsuario.CLAVE))) {
            usuario.setClave(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CLAVE)));
        }
        usuario.setFechaIngreso(DateHelper.convertirStringADate(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.FECHAINGRESO)), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        if (!cursor.isNull(cursor.getColumnIndex(ColumnaUsuario.CODIGOUSUARIOINGRESO))) {
            usuario.setCodigoUsuarioIngreso(cursor.getString(cursor.getColumnIndex(ColumnaUsuario.CODIGOUSUARIOINGRESO)));
        }
        return usuario;
    }

    private ContentValues convertirObjetoAContentValues(Usuario usuario) throws ParseException {
        ContentValues contentValues = new ContentValues();
        if (!usuario.getCodigoUsuario().isEmpty()) {
            contentValues.put(ColumnaUsuario.CODIGOUSUARIO, usuario.getCodigoUsuario());
        }
        if (!usuario.getPerfil().getCodigoPerfil().isEmpty()) {
            contentValues.put(ColumnaUsuario.CODIGOPERFIL, usuario.getPerfil().getCodigoPerfil());
        }
        if (!usuario.getNombre().isEmpty()) {
            contentValues.put(ColumnaUsuario.NOMBRE, usuario.getNombre());
        }
        if (!usuario.getContrato().getCodigoContrato().isEmpty()) {
            contentValues.put(ColumnaUsuario.CODIGOCONTRATO, usuario.getContrato().getCodigoContrato());
        }
        if (!usuario.getClave().isEmpty()) {
            contentValues.put(ColumnaUsuario.CLAVE, usuario.getClave());
        }
        if (usuario.getFechaIngreso() != null) {
            contentValues.put(ColumnaUsuario.FECHAINGRESO, DateHelper.convertirDateAString(usuario.getFechaIngreso(), DateHelper.TipoFormato.yyyyMMddTHHmmss));
        }
        contentValues.put(ColumnaUsuario.CODIGOUSUARIOINGRESO, usuario.getCodigoUsuarioIngreso());
        return contentValues;
    }

    public static class ColumnaUsuario {

        public static final String CODIGOUSUARIO = "codigousuario";
        public static final String CODIGOPERFIL = "codigoperfil";
        public static final String NOMBRE = "nombre";
        public static final String CODIGOCONTRATO = "codigocontrato";
        public static final String CLAVE = "clave";
        public static final String FECHAINGRESO = "fechaingreso";
        public static final String CODIGOUSUARIOINGRESO = "codigousuarioingreso";
    }

    public static final String CREAR_SCRIPT =
            "create table " + NombreTabla + " (" +
                    ColumnaUsuario.CODIGOUSUARIO + " " + STRING_TYPE + " not null," +
                    ColumnaUsuario.CODIGOPERFIL + " " + STRING_TYPE + " null," +
                    ColumnaUsuario.NOMBRE + " " + STRING_TYPE + " not null," +
                    ColumnaUsuario.CODIGOCONTRATO + " " + STRING_TYPE + " null," +
                    ColumnaUsuario.CLAVE + " " + STRING_TYPE + " null," +
                    ColumnaUsuario.FECHAINGRESO + " " + STRING_TYPE + " null," +
                    ColumnaUsuario.CODIGOUSUARIOINGRESO + " " + STRING_TYPE + " null," +
                    " primary key (" + ColumnaUsuario.CODIGOUSUARIO + ")" +
                    " FOREIGN KEY (" + ColumnaUsuario.CODIGOCONTRATO + ") REFERENCES " +
                    ContratoDao.NombreTabla + "( " + ContratoDao.ColumnaContrato.CODIGOCONTRATO + "), " +
                    " FOREIGN KEY (" + ColumnaUsuario.CODIGOPERFIL + ") REFERENCES " +
                    PerfilDao.NombreTabla + "( " + PerfilDao.ColumnaPerfil.CODIGOPERFIL + ")" +
                    ")";

    public static final String BORRAR_SCRIPT = "DROP TABLE IF EXISTS " + NombreTabla;
}
