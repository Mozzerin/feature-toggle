const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const path = require('path');

module.exports = {
    entry: './src/index',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, '../feature-toggle-web/src/main/resources/static'),
        publicPath: '/feature-toggle/'
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js']
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(ts|tsx)$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    },
                },
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './public/index.html'
        })
    ],
    devServer: {
        host: 'localhost', // where to run
        historyApiFallback: true,
        port: 3000, //given port to exec. app
        open: true,  // open new tab
        hot: true // Enable webpack's Hot Module Replacement
    }
};
