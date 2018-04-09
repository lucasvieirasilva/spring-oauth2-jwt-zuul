module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);

    require('time-grunt')(grunt);

    var appConfig = {
        app: 'src',
        dist: 'src/main/webapp'
    };

    grunt.initConfig({
        site: appConfig,
        copy: {
            dist: {
                files: [{
                    expand: true,
                    cwd: 'bower_components/',
                    src: '**',
                    dest: '<%= site.dist %>/lib'
                }]
            }
        },
        sass: {
            dist: {
                options: {
                    style: 'expanded'
                },
                files: {
                    '<%= site.dist %>/styles/style.css': '<%= site.app %>/scss/style.scss'
                }
            }
        },
    });
}