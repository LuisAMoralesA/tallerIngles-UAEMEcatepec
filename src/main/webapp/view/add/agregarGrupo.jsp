<%
    //Accede a la base de datos y accede a los datos del usuario principal
    ArrayList <Category> categorias = base.obtenerCategorias();
    Iterator <Category> iteradorCat = categorias.iterator();
    Category perCat = null;

    ArrayList <Grade> nivel = base.obtenerNivel();
    Iterator <Grade> iteradorNivel = nivel.iterator();
    Grade perNivel = null;
%>

<!-- Categoria del grupo -->
<label for = "grade">
    Categoria a elegir:
</label>
<select name="grade" id="grade">
<%
    while(iteradorNivel.hasNext()){
        perNivel = iteradorNivel.next();                           
        int idNivel = perNivel.getId_grade();
        String descNivel = perNivel.getDescription_grade();
%>
    <option value= <%=idNivel%>> 
        <%=descNivel%> 
    </option>
    <%}%>
 </select>
 
<!-- Nivel -->
<label for = "level">
    Nivel del 1 al 3:
</label>
<input type = "number" name = "level" id = "level" min = 1 max = 3/>

<!-- Categoria -->
<label for = "category">
    Grupos a elegir:
</label>
<select name="category" id="category">
<%
        while(iteradorCat.hasNext()){
            perCat = iteradorCat.next();                           
            int id = perCat.getId_category();
            String desc = perCat.getDescription_category();
%>
        <option value= <%=id%>> 
            <%=desc%> 
        </option>
        <%}%>
 </select>