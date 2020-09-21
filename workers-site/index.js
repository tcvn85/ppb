import { getAssetFromKV, mapRequestToAsset, NotFoundError } from '@cloudflare/kv-asset-handler'
import ppbSSR from './ppb-ssr/ppb.ssr.core';

/**
 * The DEBUG flag will do two things that help during development:
 * 1. we will skip caching on the edge, which makes it easier to
 *    debug.
 * 2. we will return an error message on exception in your Response rather
 *    than the default 404.html page.
 */
const DEBUG = false

addEventListener('fetch', event => {
    try {
        event.respondWith(handleEvent(event))
    } catch (e) {
        if (DEBUG) {
            return event.respondWith(
                new Response(e.message || e.toString(), {
                    status: 500,
                }),
            )
        }
        event.respondWith(internalServerError(new Error('Internal server error')))
    }
})

async function handleEvent(event) {
    const url = new URL(event.request.url)
    let options = {}

    try {
        if (DEBUG) {
            // customize caching
            options.cacheControl = {
                bypassCache: true,
            }
        }

        let asset = null;
        try {
            asset = await getAssetFromKV(event, options)
        } catch (e) {
            if (!(e instanceof NotFoundError)) {
                return internalServerError(e);
            }
        }
        if (asset) {
            const response = new Response(asset.body, asset);
            response.headers.set('X-XSS-Protection', '1; mode=block')
            response.headers.set('X-Content-Type-Options', 'nosniff')
            response.headers.set('X-Frame-Options', 'DENY')
            response.headers.set('Referrer-Policy', 'unsafe-url')
            response.headers.set('Feature-Policy', 'none')
            return response
        }

        const page = ppbSSR.render({uri: url.pathname});
        if (!page)
            return notFound();

        const response = new Response(page, {status: 200})
        response.headers.set('content-type', 'text/html; charset=utf8')
        return response

    } catch (e) {
        return internalServerError(e);
    }
}

async function notFound() {
    try {
        let notFoundResponse = await getAssetFromKV(event, {
            mapRequestToAsset: req => new Request(`${new URL(req.url).origin}/404.html`, req),
        })

        return new Response(notFoundResponse.body, { ...notFoundResponse, status: 404 })
    } catch (e) {}
}

function internalServerError(e) {
    new Response(e.message || e.toString(), { status: 500 })
}