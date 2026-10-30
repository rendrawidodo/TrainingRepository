app.service('myserv', function() {
          this.getServiceList = function () {
    return [{"serviceName":"Demo.services:calculator","description":"Ini adalah demo service calculator.\r\n\r\nKamu dapat menggunakan input operational dengan isi value artihmatic type\r\n1. penjumlahan\r\n2. pengurangan\r\n3. perkalian\r\n4. pembagian\r\n\r\nSelamat mencoba !","serviceinputs":[{"inputName":"arithmaticType","inputType":"String","inputComments":"no comment","required":true,"wrapperType":"--"},{"inputName":"num1","inputType":"String","inputComments":"no comment","required":true,"wrapperType":"--"},{"inputName":"num2","inputType":"String","inputComments":"no comment","required":true,"wrapperType":"--"}],"serviceoutputs":[{"outputName":"result","outputType":"String","outputComments":"no comment","required":true,"wrapperType":"--"}]}];
}
this.getISEndpoint = function() { 
 return 'http://vmi3394708.contaboserver.net:5555/';
}
this.getAPIList = function() { 
 return [{"name":"Demo.ws:demoRad","spec":{"swagger":"2.0","info":{"description":"Ini adalah REST API Calculator !","version":"1.0","title":"demo"},"host":"vmi3394708.contaboserver.net:5555","basePath":"/rad/Demo.ws:demoRad","consumes":["application/json"],"produces":["application/json"],"paths":{"/demo/calculator":{"post":{"description":"Ini adalah demo service calculator.\r\n\r\nKamu dapat menggunakan input operational dengan isi value artihmatic type\r\n1. penjumlahan\r\n2. pengurangan\r\n3. perkalian\r\n4. pembagian\r\n\r\nSelamat mencoba !","operationId":"democalculator_POST_1","consumes":["application/x-www-form-urlencoded"],"parameters":[{"name":"arithmaticType","in":"formData","required":true,"type":"string"},{"name":"num1","in":"formData","required":true,"type":"string"},{"name":"num2","in":"formData","required":true,"type":"string"}],"responses":{"200":{"description":"OK","schema":{"$ref":"#/definitions/democalculator_POST_response"}},"401":{"description":"Access Denied"}}}}},"definitions":{"democalculator_POST_response":{"type":"object","required":["result"],"properties":{"result":{"type":"string"}}}},"schemes":["HTTP"]}}];
}
this.getCreatedTime = function() { 
 return "06-10-2026 06:28:49 CEST";
}
this.getPackageInfo = function(){
 return{"packageName":"Demo","createdDate":"29-06-2026 06:53:16 CEST","version":"1.0"};
}
});
