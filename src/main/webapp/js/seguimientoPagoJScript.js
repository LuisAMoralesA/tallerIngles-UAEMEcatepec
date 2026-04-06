function cambioColor(elementoSeleccionado){
    var opcionSeleccionada = $(elementoSeleccionado).val();
    if(opcionSeleccionada == "0"){
        $(elementoSeleccionado).css("background-color", "rgba(245, 39, 39, 1)");
    }
    else{
        $(elementoSeleccionado).css("background-color", "rgba(63, 166, 83, 1)");
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

