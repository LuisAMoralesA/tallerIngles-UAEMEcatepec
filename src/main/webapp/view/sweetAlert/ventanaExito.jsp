<%
    String mensaje = (String) sesion.getAttribute("actualizacionCompleta");
    String mensajeUsuario = (String) sesion.getAttribute("userNameRegistrado");
    if (mensaje != null && !mensaje.isEmpty()){
%>
    <script>
        Swal.fire({
            icon: "success",
            title: "<%= mensaje %>",
            text: "<%= mensajeUsuario %>",
            draggable: true
          });
    </script>
<%}
    sesion.setAttribute("actualizacionCompleta", null);
    sesion.setAttribute("userNameRegistrado", null);
%>