// @author yucheng @since 1
module.exports = function(grunt) {

	grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    
    // uglify configuraion
    uglify: {
      build: {
        src: 'src/main/webapp/resources/angular/app/**/*.js',
        dest: 'src/main/webapp/resources/angular/build/yummet.min.js'
      }
    }
  });
 
  // load uglify plugin
  grunt.loadNpmTasks('grunt-contrib-uglify');
 
  // Run the task
  grunt.registerTask('default', ['uglify']);
 
};