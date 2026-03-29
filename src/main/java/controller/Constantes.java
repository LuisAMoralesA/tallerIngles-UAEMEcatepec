package controller;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
/**
 * Clase que contiene constantes que se ejecutan en todo el programa
 * @author Luis Morales
 */
public class Constantes {

    public static final String DOMINIO =                                        "/tallerDeInglesUAEM/"; //LISTO
    public static final String DOMINIO_IMAGENES =                               DOMINIO + "Images/";    //LISTO
    public static final String DOMINIO_CSS =                                    DOMINIO + "css/";       //LISTO
    public static final String DOMINIO_JS =                                     DOMINIO + "js/";
    public static final String DOMINIO_JASPERREPORTS =                          "reports/";             //LISTO
    public static final String DOMINIO_VENTANAS =                               DOMINIO + "view/";      //LISTO
    public static final LocalDate HOY =                                         LocalDate.now();
    public static final String    MES =                                         HOY.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
    
    public static final class Titulos{
        //Constantes para los titulos de los JSP
        public static final String TITULO_ACTUALIZAR_INFORMACION =              "Modificar Informacion Personal";
        public static final String TITULO_ACTUALIZAR_USUARIO =                  "Modificar Informacion de Acceso";
        public static final String TITULO_AGREGAR_INFORMACION =                 "Agregar Registro";
        public static final String TITULO_ASIGNAR_CALIFICACIONES =              "Asignacion de Calificaciones";
        public static final String TITULO_CALENDARIO =                          "Calendario de Mensualidades";
        public static final String TITULO_LISTA_ADMIN =                         "Lista de Administradores";
        public static final String TITULO_LISTA_ALUMNOS =                       "Lista de Alumnos";
        public static final String TITULO_LISTA_DOCUMENTOS =                    "Documentos Disponibles";
        public static final String TITULO_LISTA_GRUPOS =                        "Lista de Grupos Existentes";
        public static final String TITULO_LISTA_TEACHERS =                      "Lista de Profesores";
        public static final String TITULO_LOGIN_ADMIN =                         "Inicio de Sesion | Administradores";
        public static final String TITULO_LOGIN_ALUMNO =                        "Inicio de Sesion | Alumnos";
        public static final String TITULO_LOGIN_TEACHER =                       "Inicio de Sesion | Profesores";
        public static final String TITULO_MENU_ADMIN =                          "Control Escolar  | Administradores";
        public static final String TITULO_MENU_ALUMNO =                         "Control Escolar  | Alumnos";
        public static final String TITULO_MENU_TEACHER =                        "Control Escolar  | Profesores";
        public static final String TITULO_SEGUIMIENTO_PAGO =                    "Seguimiento de Pago de Alumno";
        public static final String TITULO_SESION_EXPIRADA =                     "Sesion Expirada";
        public static final String TITULO_VISTA_CALIFICACIONES =                "Menu de Calificaciones";
    }
    
    public static final class Imagenes {
        //Constantes para URLs de Imagenes
        public static final String URL_FONDO1 =                                 DOMINIO_IMAGENES + "Fondo1.jpg";
        public static final String URL_FONDO2 =                                 DOMINIO_IMAGENES + "Fondo2.jpg";
        public static final String URL_FONDO3 =                                 DOMINIO_IMAGENES + "Fondo3.jpg";
        public static final String URL_FONDO4 =                                 DOMINIO_IMAGENES + "Fondo4.jpg";
        public static final String URL_FONDO5 =                                 DOMINIO_IMAGENES + "Fondo5.jpg";
        public static final String URL_FOTO_ALUMNOS =                           DOMINIO_IMAGENES + "FotoAlumnos.jpg";
        public static final String URL_FOTO_CURSOS =                            DOMINIO_IMAGENES + "FotoCursos.jpg";
        public static final String URL_LOGO_TALLER1 =                           DOMINIO_IMAGENES + "Logo_Taller1.png";
        public static final String URL_LOGO_TALLER1_BN =                        DOMINIO_IMAGENES + "Logo_Taller1_BN.png";
        public static final String URL_LOGO_TALLER2 =                           DOMINIO_IMAGENES + "Logo_Taller2.png";
        public static final String URL_LOGO_TALLER2_BN =                        DOMINIO_IMAGENES + "Logo_Taller2_BN.png";
        public static final String URL_MENU =                                   DOMINIO_IMAGENES + "Menu.png";
        public static final String URL_NINOS1 =                                 DOMINIO_IMAGENES + "Niños1.jpg";
        public static final String URL_UAEMEX1 =                                DOMINIO_IMAGENES + "UAEMex1.jpg";
        public static final String URL_UAEMEX2 =                                DOMINIO_IMAGENES + "UAEMEX2.jpg";
        public static final String URL_CORREO =                                 DOMINIO_IMAGENES + "correo.png";
        public static final String URL_FACEBOOK =                               DOMINIO_IMAGENES + "facebook.png";
        public static final String URL_SESION_EXPIRADA =                        DOMINIO_IMAGENES + "sesionExpirada1.png";
        public static final String URL_UAEM =                                   DOMINIO_IMAGENES + "uaem.png";
        public static final String URL_UAEM_BN =                                DOMINIO_IMAGENES + "uaem_BN.png";
        public static final String URL_USER =                                   DOMINIO_IMAGENES + "user.png";
        public static final String URL_USER2 =                                  DOMINIO_IMAGENES + "user2.png";
        public static final String URL_USER3 =                                  DOMINIO_IMAGENES + "user3.png";
    }
    
    public static final class EstilosCSS {
        //Constantes para URLs para Hojas de Estilo en Cascada (CSS) de los JSP
        public static final String URL_CSS_ACTUALIZAR_INFO =                    DOMINIO_CSS + "styleActualizar.css";
        public static final String URL_CSS_ACTUALIZAR_USER =                    DOMINIO_CSS + "styleActualizarUsuario.css";
        public static final String URL_CSS_AGREGAR_USER =                       DOMINIO_CSS + "styleAgregarInfo.css";
        public static final String URL_CSS_BOTONES =                            DOMINIO_CSS + "styleButtons.css";
        public static final String URL_CSS_CALENDARIO =                         DOMINIO_CSS + "styleCalendario.css";
        public static final String URL_CSS_ASIGNAR_CALIFICACIONES =             DOMINIO_CSS + "styleCalificaciones.css";
        public static final String URL_CSS_INDEX =                              DOMINIO_CSS + "styleIndex.css";
        public static final String URL_CSS_LISTA =                              DOMINIO_CSS + "styleListas.css";
        public static final String URL_CSS_LOGIN =                              DOMINIO_CSS + "styleLogin.css";
        public static final String URL_CSS_MENU_ADMIN =                         DOMINIO_CSS + "styleMenuAdmin.css";
        public static final String URL_CSS_MENU_ALUMNO =                        DOMINIO_CSS + "styleMenuAlumno.css";
        public static final String URL_CSS_MENU_OPCIONES =                      DOMINIO_CSS + "styleMenuOpciones.css";
        public static final String URL_CSS_MENU_TEACHER =                       DOMINIO_CSS + "styleMenuTeacher.css";
        public static final String URL_CSS_SEGUIMIENTO =                        DOMINIO_CSS + "styleSeguimiento.css";
        public static final String URL_CSS_SESION_EXPIRADA =                    DOMINIO_CSS + "styleSesionExpirada.css";
        public static final String URL_CSS_VISTA_CALIFICACIONES=                DOMINIO_CSS + "styleVistaCalificaciones.css";
    }
    
    public static final class Reportes {
        public static final String URL_JASPER_BITACORA_PROFESORES =             DOMINIO_JASPERREPORTS + "BitacoraProfesores.jasper";
        public static final String URL_JASPER_BITACORA_ALUMNOS =                DOMINIO_JASPERREPORTS + "BitacorasDeAlumnos.jasper";
        public static final String URL_JASPER_LISTA_CALIFICACIONES =            DOMINIO_JASPERREPORTS + "ListaCalificaciones.jasper";
        public static final String URL_JASPER_LISTA_SEGUIMIENTO_PAGO =          DOMINIO_JASPERREPORTS + "ListaSeguimientoPago.jasper";
        public static final String URL_XML_BITACORA_PROFESORES =                DOMINIO_JASPERREPORTS + "BitacoraProfesores.jrxml";
        public static final String URL_XML_BITACORA_ALUMNOS =                   DOMINIO_JASPERREPORTS + "BitacorasDeAlumnos.jrxml";
        public static final String URL_XML_LISTA_CALIFICACIONES =               DOMINIO_JASPERREPORTS + "ListaCalificaciones.jrxml";
        public static final String URL_XML_LISTA_SEGUIMIENTO_PAGO =             DOMINIO_JASPERREPORTS + "ListaSeguimientoPago.jrxml";
    }
    
    public static final class JavaScript {
        public static final String URL_JS_LOGIN =                               DOMINIO_JS + "login.js";
        public static final String URL_JS_MENSAJES_EMERGENTES=                  DOMINIO_JS + "mensajesEmergentes.js";
    }
    
    public static final class VentanasJSP {
        public static final String URL_INDEX =                                  DOMINIO + "index.html";
        public static final String URL_ACTUALIZAR_INFORMACION =                 DOMINIO_VENTANAS + "actualizarInformacion.jsp";
        public static final String URL_ACTUALIZAR_USUARIO =                     DOMINIO_VENTANAS + "actualizarUsuario.jsp";
        public static final String URL_AGREGAR_INFORMACION =                    DOMINIO_VENTANAS + "agregarInformacion.jsp";
        public static final String URL_ASIGNAR_CALIFICACIONES =                 DOMINIO_VENTANAS + "asignarCalificaciones.jsp";
        public static final String URL_CALENDARIO =                             DOMINIO_VENTANAS + "calendario.jsp";
        public static final String URL_LISTA_ADMIN =                            DOMINIO_VENTANAS + "listaAdministradores.jsp";
        public static final String URL_LISTA_ALUMNOS =                          DOMINIO_VENTANAS + "listaAlumnos.jsp";
        public static final String URL_LISTA_DOCUMENTOS =                       DOMINIO_VENTANAS + "listaDocumentos.jsp";
        public static final String URL_LISTA_GRUPOS =                           DOMINIO_VENTANAS + "listaGrupos.jsp";
        public static final String URL_LISTA_TEACHERS =                         DOMINIO_VENTANAS + "listaTeachers.jsp";
        public static final String URL_LOGIN_ADMIN =                            DOMINIO_VENTANAS + "loginAdministrador.jsp";
        public static final String URL_LOGIN_ALUMNO =                           DOMINIO_VENTANAS + "loginAlumno.jsp";
        public static final String URL_LOGIN_TEACHER =                          DOMINIO_VENTANAS + "loginTeacher.jsp";
        public static final String URL_MENU_ADMIN =                             DOMINIO_VENTANAS + "menuAdministrador.jsp";
        public static final String URL_MENU_ALUMNO =                            DOMINIO_VENTANAS + "menuAlumno.jsp";
        public static final String URL_MENU_TEACHER =                           DOMINIO_VENTANAS + "menuTeacher.jsp";
        public static final String URL_SEGUIMIENTO_PAGO =                       DOMINIO_VENTANAS + "seguimientoPago.jsp";
        public static final String URL_SESION_EXPIRADA =                        DOMINIO_VENTANAS + "sesionExpirada.jsp";
        public static final String URL_VISTA_CALIFICACIONES =                   DOMINIO_VENTANAS + "vistaCalificaciones.jsp";
    }
    
    public static final class LinksExternos {
        //Constantes para obtener recursos de otras paginas externas a la aplicacion web
        public static final String URL_CSS_FONTAWESOME = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css";
        public static final String URL_CSS_SWEETALERT = "https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.min.css";
        public static final String URL_JS_SWEETALERT = "https://cdn.jsdelivr.net/npm/sweetalert2@11.19.1/dist/sweetalert2.all.min.js";
        public static final String URL_CSS_BOOTSTRAP = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css";
        public static final String URL_CSS_DATATABLES = "https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css";
        public static final String URL_JS_JQUERY = "https://code.jquery.com/jquery-3.7.0.js";
        public static final String URL_JS_JQUERY_DATATABLES = "https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js";
        public static final String URL_JS_DATATABLES = "https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js";
    }
    
    public static final class Servlets {
        public static final String SERVLET_AGREGAR_ADMIN = "../addAdmin";
        public static final String SERVLET_AGREGAR_GRUPO = "../addGroup";
        public static final String SERVLET_AGREGAR_ALUMNO = "../addStudent";
        public static final String SERVLET_AGREGAR_TEACHER = "../addTeacher";
        public static final String SERVLET_CERRAR_SESION = "../cerrarSesion";
        public static final String SERVLET_ELIMINAR_INFORMACION = "../deleteInformation";
        public static final String SERVLET_SESION_ADMIN = "../loginAdmin";
        public static final String SERVLET_SESION_ALUMNO = "../loginStudent";
        public static final String SERVLET_SESION_TEACHER = "../loginTeacher";
        public static final String SERVLET_REGISTRO_ALUMNO = "../registerStudent";
        public static final String SERVLET_GENERAR_REPORTES = "../reportesServlet";
        public static final String SERVLET_ACTUALIZAR_CALIFICACIONES = "../updateGrade";
        public static final String SERVLET_ACTUALIZAR_INFORMACION = "../updateInfo";
        public static final String SERVLET_ACTUALIZAR_PAGOS = "../updatePay";
        public static final String SERVLET_ACTUALIZAR_CALENDARIO = "../updateSchedule";
        public static final String SERVLET_ACTUALIZAR_USUARIO = "../updateUser";
    }
}
