// Karma configuration
// @author yucheng @since 1
module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../',

    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],

    // list of files / patterns to load in the browser
    files: [
	'../../assets/js/angular_v1.3.14/angular.js', // all needed resources
	'../../assets/js/angular_v1.3.14/angular-loader.js',
	'../../assets/js/angular_v1.3.14/angular-animate.js',
	'../../assets/js/angular_v1.3.14/angular-cookies.js',
	'../../assets/js/angular_v1.3.14/angular-route.js',
	'app/services/user/*.js',
	'app/services/animation/*.js',
	'app/services/authentication/*.js',
	'app/services/perf/*.js',
	'app/services/context/*.js',
	'app/services/logging/*.js',
	'app/services/post/*.js',
	'app/services/profile/*.js',
	'app/services/search/*.js',
	'app/components/**/*.js', // all logic code
	'test/angular-mocks.js',
	'test/*Test.js' // all test code
    ],
    // list of files to exclude
    exclude: [
    '../../../assets/js/angular_v1.3.14/*.min.js', // all needed resources
    ],

    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },

    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'junit'],
    
    // the default configuration
    junitReporter: {
      outputFile: 'test-results.xml',
      suite: ''
    },

    // web server port
    port: 9876,

    // enable / disable colors in the output (reporters and logs)
    colors: true,

    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // start these browsers
    //browsers: ['Chrome', 'Safari', 'Opera', 'IE', 'Firefox'],
    browsers: ['Chrome'],
    
    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
