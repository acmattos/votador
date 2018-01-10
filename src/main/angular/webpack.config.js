var path = require('path');

module.exports = function(env){
   const wpConfigPath = 
      path.resolve(__dirname, 'config', `webpack.config.${env}.js`);
   const wpConfig = require(wpConfigPath)(env);
   return wpConfig;
}