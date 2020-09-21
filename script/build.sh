#!/bin/bash

lein clean
shadow-cljs release spa
shadow-cljs release ssr
