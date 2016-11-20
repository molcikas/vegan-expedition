var path = require('path')
const ExtractTextPlugin = require('extract-text-webpack-plugin')

module.exports = {
  entry: ['whatwg-fetch', './index'],
  output: {
    path: path.resolve('./bundle/'),
    publicPath: '/bundle/',
    filename: '[name].bundle.js'
  },
  devtool: 'source-map',
  module: {
    loaders: [{
      test: /\.jsx?$/,
      exclude: /(node_modules|bower_components)/,
      loader: 'babel-loader',
      query: {
        presets: ['es2015', 'react', 'stage-0']
      }
    },
    {
      test: /\.css$/,
      loader: ExtractTextPlugin.extract("style-loader", "css-loader")
    },
    {
      test: /\.less$/,
      loader: ExtractTextPlugin.extract('style-loader', 'css-loader!less-loader')
    },
    {
      test: /\.(jpe?g|gif|png)$/,
      loader: require.resolve('file-loader') + '?name=[path][name].[ext]'
    }
  ]},
  plugins: [
    new ExtractTextPlugin("/styles/[name].css")
  ],
  resolve: {
    extensions: ['', '.js', '.jsx']
  },
};