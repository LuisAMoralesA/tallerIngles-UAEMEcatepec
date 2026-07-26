<!--Estatus del profesor-->
<label for="status"> Estatus del Profesor: </label>
<select name="status" id="status">
    <option value = "VIGENTE" > VIGENTE </option>
    <option value = "BAJA" > BAJA </option>
</select>

<!--Grupo a asignar-->
<label for="grupo"> Grupo Asignado: </label>
<select name="grupo" id="grupo">
    <option value = "0"> Ningun Grupo Asignado </option>
    <%
        ArrayList <ConsultaGrupos> grupos = base.obtenerDatosGrupos();
        Iterator <ConsultaGrupos> iterGrupos = grupos.iterator();
        ConsultaGrupos perGrupos = null;
        while(iterGrupos.hasNext()){
            perGrupos = iterGrupos.next();
            int idGrupo = perGrupos.getId_group();
            String datosGrupo = perGrupos.getDescription_grade() + " " + 
            perGrupos.getLevel_group() + ": " + perGrupos.getDescription_category();
    %>
    <option value = "<%=idGrupo%>"> <%=datosGrupo%> </option>
        <%}%>
</select>

<!--Salon de clases-->
<label for="classroom">Salon de Clases <br> <b>(Letra de edificio y número sin caracter especial):</b></label>
<input type="text" name="classroom" id="classroom" placeholder ="Salon de Clases" >