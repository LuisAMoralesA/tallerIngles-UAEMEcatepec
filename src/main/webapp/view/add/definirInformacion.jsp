<%
    String opcion = request.getParameter("add");
    String encabezado = "Agregar ";
    String accion = "";
    String url = "";
    String rango = "";
    if(opcion != null){
        switch(opcion){
            case "1":
                encabezado += "Estudiante";
                accion =    Constantes.Servlets.SERVLET_AGREGAR_ALUMNO;
                url =       Constantes.VentanasJSP.URL_LISTA_ALUMNOS;
                rango = "ESTUDIANTE";
                break;
            case "2":
                encabezado += "Profesor";
                accion =    Constantes.Servlets.SERVLET_AGREGAR_TEACHER;
                url =       Constantes.VentanasJSP.URL_LISTA_TEACHERS;
                rango = "PROFESOR";
                break;
            case "3":
                encabezado += "Administrador";
                accion =    Constantes.Servlets.SERVLET_AGREGAR_ADMIN;
                url =       Constantes.VentanasJSP.URL_LISTA_ADMIN;
                rango = "ADMINISTRADOR";
                break;
            case "4":
                encabezado += "Grupo";
                accion =    Constantes.Servlets.SERVLET_AGREGAR_GRUPO;
                url =       Constantes.VentanasJSP.URL_LISTA_GRUPOS;
                break;
        }
    }
%>