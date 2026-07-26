<%
    String mensaje = (String) sesion.getAttribute("contraseñaIncorrecta");
    if (mensaje != null && !mensaje.isEmpty()){
%>
    <script>
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "<%= mensaje %>",
            confirmButtonColor: "#9C8412"
          });
    </script>
<%}
    sesion.setAttribute("contraseñaIncorrecta", null);
%>