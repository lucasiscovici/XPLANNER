var path = require('path')
var webpack = require('webpack')
const ExtractTextPlugin = require("extract-text-webpack-plugin")
var CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebPackPlugin = require("html-webpack-plugin");
module.exports = {
  entry: './src/main.js',
  output: {
    path: path.resolve(__dirname, './dist'),
    publicPath: '/dist/',
    filename: 'build.js'
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
        sass: [
          'vue-style-loader',
          'css-loader',
          'sass-loader?indentedSyntax=1',
          {
            loader: 'sass-resources-loader',
            options: {
              resources: path.resolve(__dirname, 'src/var.scss'), // for example
            },
          },
        ],
        scss: [
          'vue-style-loader',
          'css-loader',
          'sass-loader',
          {
            loader: 'sass-resources-loader',
            options: {
              resources: path.resolve(__dirname, 'src/var.scss'), // for example
            },
          },
        ],
      },
          // other vue-loader options go here
        }
      },

      {
        test: /\.js$/,
        loader: 'babel-loader',
        exclude: /node_modules/,
        query:{
          presets: ['es2015'],
          plugins: ["transform-es2015-template-literals"]
        },
        include: [

    path.join(__dirname, 'src/api')
    // path.join(__dirname, 'common')
   ]
      },
      {
        test: /\.css$/,
        use: ExtractTextPlugin.extract({
          fallback: "style-loader",
          use: "css-loader"
        })
      },
      {
        test: /\.(png|jpg|gif|svg)$/,
        loader: 'file-loader',
        options: {
          name: '[name].[ext]?[hash]'
        }
      },
      {
        test: /\.html$/,
        use: [
          {
            loader: "html-loader",
            options: { minimize: true }
          }
        ]
      }
    ]
  },
  resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    }
  },
  devServer: {
    historyApiFallback: true,
    noInfo: true,
    hot:true
  },
  performance: {
    hints: false
  },
  plugins: [new ExtractTextPlugin("main.css")],
  devtool: '#eval-source-map'
}

if (process.env.NODE_ENV === 'production') {
  module.exports.devtool = '#source-map'
  module.exports.output.publicPath = './'

  // http://vue-loader.vuejs.org/en/workflow/production.html
  module.exports.plugins = (module.exports.plugins || []).concat([
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
     // new webpack.optimize.UglifyJsPlugin({
     //  compress: {
     //    warnings: false
     //  }
     //      }),
    //  new CopyWebpackPlugin([
    //   { from: './index.html', to: module.exports.output.publicPath+"/index.html" }
    // ]),
    // new webpack.optimize.UglifyJsPlugin({
    //   sourceMap: true,
    //   compress: {
    //     warnings: false
    //   }
    // }),
new HtmlWebPackPlugin({
      template: "./index.html",
      filename: "./index.html"
    }),
    new CopyWebpackPlugin([
      {from:'src/assets',to:'src/assets'} 
    ]),

    new webpack.LoaderOptionsPlugin({
      minimize: true,
      options:{
          assetsPublicPath: 'assets'

      }
    })
  ])
}
