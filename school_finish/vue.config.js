module.exports={
    devServer:{
        proxy:{
            '/api':{
                target:"http://localhost:8181/School_Finish_1_war_exploded",
                changeOrigin:true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    }
}