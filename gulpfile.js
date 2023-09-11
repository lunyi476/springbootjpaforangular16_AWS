//import gulp from 'gulp';
//+const { series, parallel, src, dest, task } = gulp; // default export
import gulp from 'gulp';  // default export
import  { deleteAsync } from 'del'; 
import log from 'fancy-log';  // default export

/** 
 *  1. Del module is ES module, but current gulpfile.js is CommonJS module 
 *  2. index.js entry point of Del module exporting async function eleteAsync 
 *  3. import() returns a promise, so, to handle the asynchronous steps after the import() expression, use either: 
 *      .then() syntax; or async / await syntax for CommonJS to import module from ES module
 *  4. (async () => {} ) (); call returned one Promise, series does not registed such Argument
 *  5. Because await is only valid inside async functions and modules, which themselves are asynchronous 
 *     and return promises, the await expression never blocks the main thread and only defers execution 
 *     of code that actually depends on the result, i.e. anything after the await expression
 * 
 * 
 *  we add "type": module into package.json
 *  and this gulpfile.js will become ES module instead of CommonJS module any more
 *  so, we import gulp and log module from CommonJS , and  del module from ES.
 * 
*/

/*
function defaultTask(cb) {
  // place code for your default task here
  gulp.series(
  clean,
  copyAngularCodeTask);
  
  cb();
}*/


const paths = {
  angular_src: 'src/main/angular16forspringbootjpa/dist/*',
  angular_dist: 'src/main/resources/static/'
};


function clean(cb)  {
	log('removing the old files in the directory' + deleteAsync);
    /**     
     Now problem is when run   deleteAsync('src/main/resources/static*', {force:true}) :
     error thrown as:
     The following tasks did not complete: default, clean
 	 Did you forget to signal async completion?
 	 
 	 possible solution:  https://gulpjs.com/docs/en/getting-started/async-completion/#using-async-await
 	**/
   deleteAsync('src/main/resources/static/*', {force:true}); 
   /*.then((data) =>{
        return data;
    }).catch((err) =>{
        return err;
    }); */
    cb();
}

function copyAngularCodeTask(cb) {
  log('copying Angular code into the directory' + '----------' + `${paths.angular_src}`  + '-------'  + `${paths.angular_dist}` );

  gulp.src(`${paths.angular_src}`)
        .pipe(gulp.dest(`${paths.angular_dist}`));
  cb();
}
	

export default gulp.series(
  clean,
  copyAngularCodeTask
);


//export default  defaultTask;
