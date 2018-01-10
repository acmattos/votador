const webpack = require('webpack');
const path = require('path');

const BaseHrefWebpackPlugin = require('base-href-webpack-plugin').BaseHrefWebpackPlugin;
const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const OptimizeCssAssetsWebpackPlugin = require ('optimize-css-assets-webpack-plugin');

const dist = 'dist-prod';
const VENDOR_LIBS = [
   'angular',
   'angular-local-storage',
   'angular-ui-bootstrap',
   'angular-ui-router',
   'bootstrap',
   'jquery',
   'jwt-decode'
];

const OAUTH_SERVER_URL_LOCAL = 'http://localhost:8080';

module.exports = function(env){
   const BASE_URI =  (env == 'development') 
                     ? ''
                     : '/sistema';
   
   const LOGIN_URL     = (env == 'development') 
                         ? 'http://localhost:8080' 
                         : 'http://localhost:8080';
   return {
      entry: {
         bundle: './app/index.js',
         vendor: VENDOR_LIBS
      },
      output: {
         path: path.join(__dirname, '..', dist),
         filename: '[name].[chunkhash].js'
      },
      module:{
         rules:[
            {
               test: /\.js$/,
               exclude: /node_modules/,
               use: {
                  loader: 'babel-loader',
                  options: {
                     presets: ['env', 'stage-0']
                  }
               }
            },
            {
               test: /\.js$/,
               enforce: 'pre',
               exclude: /node_modules/,
               use: [
                  {
                     loader: "jshint-loader"
                  }
               ]
            },
            {
               use: ['style-loader', 'css-loader', 'postcss-loader'],
               test: /\.css$/
            },
            {
               use: 'html-loader',
               test: /\.html$/
            },
            {
               test: /\.(png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot|pdf)$/, 
               loader: 'url-loader'
            },
         ]
      },
      plugins: [
         new CleanWebpackPlugin(
          [dist], 
          {verbose: true}
         ),
         new BaseHrefWebpackPlugin({
                baseHref: env == 'development' ? '/' : '/'
         }),
         // https://github.com/jantimon/html-webpack-plugin
         new HtmlWebpackPlugin({
            template: 'app/index.html',
            inject: 'body',
            hash: true,
            minify: {
               collapseWhitespace: true
            },
            showErrors: true
         }), 
         // https://github.com/NMFR/optimize-css-assets-webpack-plugin
         new OptimizeCssAssetsWebpackPlugin({
            cssProcessorOptions: { discardComments: {removeAll: true } }
         }), 
         new webpack.DefinePlugin({
            'process.env.NODE_ENV' : JSON.stringify(env),
            'env': JSON.stringify(env),
            'BASE_URI': JSON.stringify(BASE_URI),
            'LOGIN_URL': JSON.stringify(LOGIN_URL)
         }),
         new webpack.ProvidePlugin({
            "$": "jquery",
            "jQuery": "jquery"
         }),
         new webpack.optimize.CommonsChunkPlugin({
            names: ['vendor', 'manifest']
         }),
         // https://github.com/webpack-contrib/uglifyjs-webpack-plugin
         new webpack.optimize.UglifyJsPlugin({
            parallel: true,
            uglifyOptions: {
              ecma: 5,
              output: {
                comments: false
              },
              mangle: false,
              sourceMap: true
            }
         })
      ],
      devServer: {
         contentBase: path.resolve(__dirname, '..', dist),
         inline: true,
         port: 80,
         proxy: [{
            logLevel:'debug',
            context: ['/oauth/token', '/eleicao/', '/funcionarios'],
            target: OAUTH_SERVER_URL_LOCAL
         }]
      }/* ,
      // https://webpack.js.org/configuration/devtool/
      devtool: 'source-map' */
   };
}
