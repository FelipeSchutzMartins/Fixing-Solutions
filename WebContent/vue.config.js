module.exports = {
    pages: {
        index: {
            entry: 'store/main.js',
        },
    },
    runtimeCompiler: true,
    chainWebpack: config => {
        config.module
            .rule('graphql')
            .test(/\.(png|svg|jpg|gif|pdf)$/)
            .use('graphql-tag/loader')
            .loader('file-loader')
            .end()
    }
}