function cambioColor(elementoSeleccionado){
    var opcionSeleccionada = $(elementoSeleccionado).val();
    if(opcionSeleccionada == "0"){
        $(elementoSeleccionado).css("background-color", "rgba(245, 39, 39, 1)");
    }
    else{
        $(elementoSeleccionado).css("background-color", "rgba(63, 166, 83, 1)");
    }
}

function cambioColorEstatus(elementoSeleccionado){
    var opcionSeleccionada = $(elementoSeleccionado).val();
    switch(opcionSeleccionada){
        case "1":
            //Verde
            $(elementoSeleccionado).css("background-color", "rgba(63, 166, 83, 1)");
            break;
        case "2":
            //Amarillo
            $(elementoSeleccionado).css("background-color", "rgba(179, 186, 15,1)");
            break;
        case "3":
            //Rojo
            $(elementoSeleccionado).css("background-color", "rgba(245, 39, 39, 1)");
            break;
    }
}
$(document).ready(function() {
    $('#tablaAlumnos').DataTable({
        "pageLength": 7, 
        "responsive": true,
        "lengthChange": false,
        "searching": false,
        info: false,
        "paging": false,
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        }
    });
    
    $('#tablaAlumnos').on("change", "select", function(){
        cambioColor(this);
    });
});

$(document).ready(function() {
    $('#tablaSeguimiento').DataTable({
        "pageLength": 1, 
        "responsive": true,
        "lengthChange": false,
        "searching": false,
        info: false,
        "paging": false,
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        }
    });
    
    $('#tablaSeguimiento').on("change", "select", function(){
        cambioColorEstatus(this);
    });
});

