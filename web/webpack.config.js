var path = require('path')
const ExtractTextPlugin = require('extract-text-webpack-plugin')

module.exports = {
  entry: './index',
  output: {
    path: path.resolve('./bundle'),
    publicPath: '/veganexpedition/bundle',
    filename: '[name].bundle.js'
  },
  devtool: 'source-map',
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /(node_modules|bower_components)/,
        loader: 'babel-loader',
        query: {
          presets: ['es2015', 'react', 'stage-0']
        }
      },
      {
        test: /\.less$/,
        loader: ExtractTextPlugin.extract('style-loader', 'css-loader!less-loader')
      },
    ]
  },
  plugins: [
    new ExtractTextPlugin("[name].css")
  ],
  resolve: {
    extensions: ['', '.js', '.jsx']
  },
};