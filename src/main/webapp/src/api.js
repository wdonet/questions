import rest from 'rest';
import defaultRequest from 'rest/interceptor/defaultRequest';
import mime from 'rest/interceptor/mime';
import errorCode from 'rest/interceptor/errorCode';
import baseRegistry from 'rest/mime/registry';
import hal from 'rest/mime/type/application/hal';

/* Convert a single or array of resources into "URI1\nURI2\nURI3..." */
const uriListConverter = {
  read(str) {
    return str.split('\n');
  },
  write(obj) {
    // If this is an Array, extract the self URI and then join using a newline
    let ret;
    if (obj instanceof Array) {
      ret = obj.map(resource => resource._links.self.href).join('\n');
    } else { // otherwise, just return the self URI
      ret = obj._links.self.href;
    }

    return ret;
  },
};

// import interceptor from 'rest/interceptor';
const interceptor = require('rest/interceptor');
const uriTemplateInterceptor = interceptor({
  request(request) {
    /* If the URI is a URI Template per RFC 6570 (http://tools.ietf.org/html/rfc6570), trim out the template part */
    const ret = request;
    if (request.path.indexOf('{') !== -1) {
      ret.path = request.path.split('{')[0];
    }
    return ret;
  },
});

const registry = baseRegistry.child();

registry.register('text/uri-list', uriListConverter);
registry.register('application/hal+json', hal);

export default rest
  .wrap(mime, { registry })
  .wrap(uriTemplateInterceptor)
  .wrap(errorCode)
  .wrap(defaultRequest, { headers: { Accept: 'application/hal+json' } });
