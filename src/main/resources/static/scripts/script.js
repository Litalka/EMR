
function diagnosisOnSubmit(){
    var id = document.getElementsByName("id")[0].value;
    var type = document.getElementsByName("type")[0].value;
    
    var action_src = "/records/" + id + "/diagnosis/" + type;
    var your_form = document.getElementById('diagnosisFormId');
    your_form.action = action_src ;
}