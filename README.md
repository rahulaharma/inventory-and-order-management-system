Inventory & Order Management System - Frontend
This is the frontend for a comprehensive Inventory and Order Management System, built with a modern, high-performance tech stack including React 19, React Router v7, and Tailwind CSS v4.

The application provides a user-friendly interface for managing products, inventory levels, customers, and sales orders. It features role-based access control to ensure that staff members can only access the features relevant to their roles.

✨ Key Features
Dashboard: An overview of key metrics and system activity.

Product Management: Full CRUD (Create, Read, Update, Delete) functionality for products.

Inventory Tracking: Monitor stock levels and manage inventory updates.

Customer Management: Maintain a database of customers with complete CRUD operations.

Order Processing:

Create new sales orders.

View a list of all orders.

Drill down into detailed order information.

User Authentication: Secure login and registration system using JWT.

Role-Based Access Control: Custom routes and components (ProtectedRoute, RoleBasedRoute) restrict access based on user roles (e.g., Sales Staff).

Interactive Notifications: User-friendly feedback through toast notifications.

🚀 Technologies Used
Framework: React 19

Bundler: Parcel v2

Routing: React Router DOM v7

Styling: Tailwind CSS v4 & Heroicons

HTTP Client: Axios

State Management: React Context API (AuthProvider)

Notifications: React Toastify

Authentication: JWT Decode

🛠️ Installation & Setup
Follow these steps to get the development environment running locally.

Prerequisites
Node.js (version 18.x or higher recommended)

npm (comes with Node.js)

1. Clone the Repository
git clone [https://github.com/rahulaharma/inventory-order-management-frontend.git](https://github.com/rahulaharma/inventory-order-management-frontend.git)
cd inventory-order-management-frontend

2. Install Dependencies
Install all the required npm packages.

npm install

3. Set Up Environment Variables
This project requires an API endpoint to connect to the backend.

Create a new file in the root of the project named .env.

Add the following variable, pointing to your backend server URL:

REACT_APP_API_BASE_URL=http://localhost:8080/api

(Adjust the URL if your backend server runs on a different port or domain.)

4. Run the Development Server
Start the Parcel development server.

npm run start

The application should now be running on http://localhost:1234.

📜 Available Scripts
In the project directory, you can run:

npm run start

Runs the app in development mode with hot-reloading.

npm run build

Builds the app for production to the dist folder. It correctly bundles React and optimizes the build for the best performance.

📂 Project Structure
The src folder is organized to maintain a clean and scalable architecture.

src/
├── pages/                # All page-level components (e.g., Dashboard, ProductList)
│   ├── AuthProvider.js     # Context for managing authentication state
│   ├── Login.js
│   ├── Register.js
│   ├── ProtectedRoute.js   # Wrapper to protect routes from unauthenticated access
│   └── RoleBasedRoute.js   # Wrapper to protect routes based on user role
├── components/           # Reusable UI components (Buttons, Forms, Modals, etc.)
├── services/             # API call functions using Axios (e.g., authService.js, productService.js)
├── utils/                # Utility functions
├── assets/               # Images, fonts, and other static assets
└── index.js              # Main entry point of the application

🔐 Authentication Flow
Login/Register: Users log in or register to receive a JSON Web Token (JWT).

Token Storage: The JWT is stored securely (e.g., in localStorage or sessionStorage).

AuthProvider: This global context wraps the application, providing user and token information to all components.

ProtectedRoute: This component wraps routes in App.js. If the user is not logged in, it redirects them to the /login page.

API Requests: The stored JWT is attached to the authorization header of every API request made with Axios, allowing the backend to verify the user's identity.

📄 License
This project is licensed under the ISC License.

✍️ Author
Created by Rahul Sharma.

In-Depth Analysis: Common Build Failures
Section 1: Deconstructing the Build Failure: An Anatomy of the Failed to resolve 'react-router/dom' Error
The build failure presented, while seemingly straightforward, represents a complex interaction between evolving JavaScript library standards and the specific resolution mechanisms of the Parcel bundler. A precise understanding of the error message is the foundational step toward an effective and lasting solution. The frustration often associated with this error stems from a misinterpretation of the bundler's output, which can lead to a cycle of ineffective troubleshooting steps like reinstalling packages.[1, 2] A granular deconstruction of the error log reveals a more nuanced problem than a simple missing module.

1.1 The Anatomy of a Parcel Error Message
The error log generated by Parcel is not a monolithic statement but a composite report from different parts of its internal architecture. Each component provides a clue to the underlying issue.

@parcel/core: This is the primary orchestrator of the Parcel build process. When @parcel/core reports a failure, it signifies a critical, build-halting event has occurred. The message 🚨 @parcel/core: Failed to resolve 'react-router/dom' from './node_modules/react-router-dom/dist/index.mjs' indicates that the build process was initiated, but a dependency could not be located from within a specific file of an installed package.[1, 3] This initial message correctly identifies the package that triggers the error (react-router-dom) but can be misleading as to the root cause.

@parcel/resolver-default: This is the specific plugin within Parcel responsible for the crucial task of dependency resolution. Its job is to take a module specifier—the string in an import or require statement (e.g., 'react-router')—and translate it into an absolute file path on the disk that can be read and processed.[4] The error message originating from this plugin, @parcel/resolver-default: Cannot load file './dom' from module 'react-router', is the most critical piece of diagnostic information.[3, 5, 6] It pinpoints the exact failure point in the resolution algorithm.

1.2 The Two Key Lines: Uncovering the True Locus of Failure
Analyzing the two principal lines of the error message in conjunction provides a clear path to diagnosis:

The Trigger: @parcel/core: Failed to resolve 'react-router/dom' from './node_modules/react-router-dom/dist/index.mjs'.[1, 3, 5] This line establishes the context. The error is not happening in the application's own source code but within a file (index.mjs) distributed as part of the react-router-dom package. This file is attempting to import something using the specifier 'react-router/dom'.

The Root Cause: @parcel/resolver-default: Cannot load file './dom' from module 'react-router'.[1, 3, 5, 6] This line reveals the precise nature of the failure. Parcel is not failing to find the react-router-dom package. Instead, it is successfully locating the react-router package but is then unable to resolve the ./dom subpath within it. It is looking for a physical file or directory named dom inside the react-router package directory and cannot find one using its default resolution strategy.

This distinction is fundamental. The common impulse is to address the first message by reinstalling or checking the version of react-router-dom.[1, 7] However, these actions are futile because react-router-dom is installed correctly. The problem lies in an incompatibility between how react-router-dom expects its peer dependency, react-router, to be resolved and how Parcel is configured to perform that resolution. The issue is not a missing or corrupt package but a misaligned contract between a library's internal structure and the bundler's resolution capabilities. This re-frames the problem from one of package management to one of toolchain configuration and library architecture.

Section 2: The Paradigm Shift in react-router: A Deep Dive into the v6 to v7 Transition
The module resolution error is not a random bug but a direct consequence of a deliberate and significant architectural refactoring within the React Router library. Understanding the evolution from version 5 through version 7 is essential to grasp why an import statement that is valid under one set of conditions fails under another.

2.1 A Brief History of React Router's API
React Router has undergone several major revisions, each altering its core API and usage patterns.

The v5 Era: In versions 5 and earlier, the react-router-dom package was the all-encompassing library for web development. Developers would import components like <BrowserRouter>, <Route>, and the crucial <Switch> component directly from it. The <Switch> component was responsible for rendering only the first <Route> that matched the current URL.[8, 9] This pattern was stable for a long period and became deeply ingrained in many legacy codebases and tutorials.

The v6 Revolution: Version 6 marked a significant breaking change, replacing the <Switch> component with a more powerful and declarative <Routes> component. This change also modified how routes were defined, favoring the element prop with a JSX element (e.g., element={<Home />}) over the component prop (e.g., component={Home}).[8, 10] The exact prop was also made redundant, as routes now matched more intelligently by default. This transition required developers to undertake significant code modifications and represented a major philosophical shift in the library's design.[8, 11]

2.2 The v7 Architectural Refactoring: Consolidation and Simplification
The transition to version 7 introduced another profound, albeit more subtle, architectural change focused on the relationship between the react-router and react-router-dom packages.

The Core Change: With version 7, react-router became the canonical, primary package containing the core routing logic and most APIs. The react-router-dom package was effectively refactored into a thin compatibility wrapper. Its main purpose is to re-export the core functionalities from react-router while providing the specific components needed for a DOM environment (like <BrowserRouter>).[12, 13] The package.json for react-router-dom v7 explicitly states that it "simply re-exports everything from react-router to smooth the upgrade path".[13]

The "Why": This change was driven by a desire to simplify the package ecosystem and create a single source of truth for the routing logic. By centralizing the core in react-router, the library becomes more platform-agnostic and easier to maintain, with platform-specific packages like react-router-dom and react-router-native serving as lightweight extensions.

The Practical Impact: This architectural decision is the direct trigger for the user's error. The react-router-dom package now contains internal code that performs imports from its peer, react-router. For instance, the file node_modules/react-router-dom/dist/index.mjs contains statements like export * from "react-router"; and, critically, import { HydratedRouter, RouterProvider } from "react-router/dom";.[3, 5] This latter import is a self-referential-looking path that relies on modern package resolution features to be correctly interpreted by a bundler as an import from the react-router package's defined "dom" export.

2.3 Versioning and Environment Constraints
A critical aspect of the v7 upgrade is its strict environment requirements. The official documentation and package information stipulate that React Router v7 requires the following minimum versions [14]:

Node.js: v20.0.0 or higher

React: v18.0.0 or higher

react-dom: v18.0.0 or higher

Any project attempting to use react-router-dom v7 in an environment that does not meet these criteria, such as the one noted in a Stack Overflow post using Node.js v18.17.1 [1], will encounter compatibility issues, even if the module resolution problem is solved. This version check is a mandatory prerequisite for any successful resolution.

The following table summarizes the key architectural differences between the major versions, providing context for the v7 changes that cause the build failure.

Version

Key Routing Component

Primary Package for Imports

Example Route Definition

Notes

v5

<Switch>

react-router-dom

<Route path="/about" component={About} />

Used component prop and often required the exact prop for precise matching.[8, 9]

v6

<Routes>

react-router-dom

<Route path="/about" element={<About />} />

Replaced <Switch> with <Routes>. Introduced the element prop. exact prop became default behavior.[8, 10]

v7

<Routes>

react-router (canonical)

<Route path="/about" element={<About />} />

react-router-dom becomes a re-export wrapper. The recommended practice is to import directly from react-router.[5, 12, 13]

Section 3: The Modern JavaScript Package Contract: Understanding Parcel's Interaction with package.exports
The second pillar of this issue lies not within the React Router library itself, but in the bundler's handling of a modern JavaScript packaging standard. The react-router v7 architecture relies heavily on a package.json feature called exports, and Parcel's default behavior regarding this feature is the direct cause of the resolution failure.

3.1 What is package.exports?
The exports field, introduced in Node.js v12.7.0, is a modern standard for defining the entry points of an npm package.[15] It provides a more powerful and explicit alternative to legacy fields like main, module, and browser. Its adoption provides several key benefits to package authors and the ecosystem at large.

Encapsulation: The primary benefit is strong encapsulation. When the exports field is present, it defines the only subpaths of the package that can be imported by consumers. Any attempt to import a file not explicitly listed in exports will result in a "Module Not Found" error.[16, 17] This creates a formal public API, preventing developers from relying on a package's internal file structure, which gives maintainers the freedom to refactor internal code without causing breaking changes for users.[17]

Conditional Exports: package.exports allows a package to resolve the same import specifier to different files based on a set of conditions. For example, a package can provide different builds for Node.js versus browser environments, or for consumers using ES Modules (import) versus CommonJS (require).[15, 17] This is how react-router can define what "react-router/dom" should resolve to.

Alias-like Mapping: The feature allows for the creation of clean, virtual import paths. A package author can map an intuitive path like my-pkg/utils to a compiled file deep within a dist folder, such as ./dist/esm/utils.mjs.[17] This improves the developer experience of using the package.

3.2 Parcel's Dependency Resolution Algorithm
Parcel implements an enhanced version of the standard Node.js module resolution algorithm to locate dependencies.[4] When it encounters a bare specifier like 'react' or 'react-router', it traverses up the directory structure from the importing file, looking for a node_modules directory that contains a package with that name.[4] Once the package directory is found, Parcel must determine which file within that package to use as the entry point. This is where the interaction with package.json fields becomes critical.

3.3 The Crucial Configuration: packageExports in Parcel
The central conflict arises from Parcel's default configuration. For reasons of backward compatibility with a vast ecosystem of older packages that do not use the exports field correctly, Parcel's default resolver (@parcel/resolver-default) does not process the package.exports field by default.[3, 12] This is the lynchpin of the entire problem.

To use modern packages like react-router v7 that rely on this feature, developers must explicitly opt-in to enable support for it. This requirement is documented in Parcel's official documentation and has been repeatedly stated by Parcel maintainers in response to GitHub issues identical to the one in question.[3, 4, 12] The feature can be enabled either in the project's package.json file or, more robustly, in a dedicated .parcelrc configuration file.[18, 19]

This situation illustrates a common tension in the JavaScript ecosystem: the asynchronous adoption of new standards. Library authors, keen to leverage modern features for better package design, will adopt standards like package.exports as soon as they are stable. Build tools, however, must prioritize stability for a wide range of projects, both old and new. They often make support for new, potentially breaking standards an opt-in feature. A developer who updates their dependencies to the latest versions without also updating their build configuration to match falls into this "compatibility gap," leading to precisely the kind of build failure observed. The error is not a bug in either the library or the bundler, but a failure to align their respective configurations.

Section 4: A Converging Diagnosis: The Intersection of Package Evolution and Bundler Configuration
By synthesizing the architectural changes in React Router v7 with the default behavior of Parcel's module resolver, a complete and unambiguous causal chain for the error emerges. The failure is not a single event but a sequence of steps where expectation and reality diverge.

4.1 The Complete Causal Chain
The build process fails due to the following sequence of events:

Application Code Import: The user's application code contains a standard import statement, such as import { createBrowserRouter, RouterProvider } from "react-router-dom";.

Internal Package Import: Parcel begins processing the react-router-dom package. It encounters the file node_modules/react-router-dom/dist/index.mjs, which contains an internal, self-referential-looking import: import { HydratedRouter, RouterProvider } from "react-router/dom";.[3, 5]

The package.exports Contract: The react-router package, a peer dependency, has a package.json file that utilizes the exports field. This field defines a mapping for the subpath "./dom", specifying which file should be served when a consumer tries to import react-router/dom.

Parcel's Default Resolution: The user's Parcel configuration is in its default state, which means support for the package.exports field is disabled.[3, 12]

Ignoring the Contract: Consequently, Parcel's @parcel/resolver-default plugin ignores the exports field in react-router's package.json. It falls back to its legacy resolution algorithm.[4]

Failed File System Lookup: The legacy algorithm attempts a direct file system lookup. It searches for a physical file or directory at the path ./node_modules/react-router/dom (with various extensions like .js, .mjs, etc.).

File Not Found: No such file or directory exists at that physical location. The path "./dom" is a virtual path defined only within the exports map.

Resolution Failure and Error: The lookup fails. @parcel/resolver-default reports that it Cannot load file './dom' from module 'react-router'. This critical failure halts the build, and @parcel/core propagates the error to the user.

This step-by-step process clarifies that the error is a direct result of Parcel not being instructed to read the "map" (package.exports) provided by the react-router library. Without this instruction, it gets lost trying to find a path that doesn't physically exist.

Section 5: A Comprehensive Resolution Framework
Resolving this build failure requires a systematic approach, beginning with foundational environment checks and then proceeding to the most appropriate solution based on the project's context and goals. The following framework provides ordered, actionable steps to diagnose and fix the issue permanently.

Before attempting any solution, it is crucial to perform a diagnostic check to ensure the environment is correctly understood.

Table 2: Diagnostic Checklist and Solution Matrix
Symptom/Check

Technical Implication

How to Verify

Recommended Solution Path

Error message contains @parcel/resolver-default: Cannot load file './dom' from module 'react-router'

Parcel is not resolving the package.exports field in react-router's package.json.

Review the full build error log.

Proceed to Step 2: Primary Solution.

Node.js version is below 20.x

The environment does not meet the minimum requirements for react-router v7.

Run node -v in the terminal.

Upgrade Node.js to the latest LTS version (v20.x or higher) before proceeding.

Project has no .parcelrc file and no parcel key in package.json

The project is using Parcel's default configuration, which has packageExports disabled.

Check for the existence of .parcelrc or a "parcel" key in package.json.

Proceed to Step 2: Primary Solution.

react-router-dom version is 7.x or higher

The project is using the modern architecture of React Router that relies on package.exports.

Run npm list react-router-dom.

Proceed with Step 2 or Step 3.

Build still fails after applying a fix

The Parcel cache may be stale, serving old, broken build artifacts.

The error persists despite correct configuration.

Proceed to Step 1: Foundational Environment Hygiene.

5.1 Step 1: Foundational Environment Hygiene (The Prerequisites)
These preliminary steps are essential to ensure that any subsequent fixes are applied to a clean and predictable environment. Stale caches and corrupted installations are common sources of persistent, phantom errors.

Clear the Parcel Cache: Parcel aggressively caches build artifacts to speed up subsequent builds. A corrupted or stale cache can cause an old error to persist even after the underlying configuration has been fixed. The first action should always be to delete the cache directory.[6, 20]

Action: In the project's root directory, delete the .parcel-cache folder.

Command: rm -rf.parcel-cache (on Linux/macOS) or delete the folder manually in Windows Explorer.

Perform a Clean Reinstallation of Dependencies: A corrupted node_modules directory or an out-of-sync lockfile can lead to unpredictable behavior. A clean reinstall ensures that all dependencies are fetched according to the package.json specification and that the lockfile is consistent.[1, 2, 7]

Action: Delete the node_modules directory and the project's lockfile (package-lock.json for npm, yarn.lock for Yarn).

Command (npm): rm -rf node_modules package-lock.json && npm install

Command (Yarn): rm -rf node_modules yarn.lock && yarn install

Verify Node.js Version: As established, react-router v7 has a strict requirement for Node.js v20.x or higher.[14]

Action: Check the active Node.js version.

Command: node -v

Remediation: If the version is below 20, it must be upgraded using a version manager like nvm (nvm install 20 && nvm use 20) or by downloading the latest LTS release from the official Node.js website.

5.2 Step 2: The Primary Solution - Configure Parcel for Modern Module Resolution
This is the recommended and most robust solution. It addresses the root cause by aligning the bundler's capabilities with the requirements of modern libraries. This future-proofs the project against similar issues with other packages that adopt the package.exports standard.

The goal is to instruct Parcel's resolver to honor the exports field in packages' package.json files. This can be achieved in two ways.

Method A: Using package.json (for simple projects)
For projects without complex build configurations, the Parcel configuration can be placed directly within the package.json file. This is the quickest way to enable the feature.[18, 19]

Method B: Using .parcelrc (Recommended)
For any non-trivial project, creating a dedicated .parcelrc configuration file is the best practice. This file explicitly defines the entire build pipeline (resolvers, transformers, packagers, etc.), making the build process more transparent and maintainable. The configuration extends Parcel's default settings and adds the necessary resolver property.[3, 4, 12]

The following table provides the exact code snippets for both methods.

Table 3: Parcel Resolver Configuration for package.exports
Configuration Method

File to Edit

JSON Snippet

package.json

package.json

json { "name": "my-project", "version": "1.0.0", //... other keys... "@parcel/resolver-default": { "packageExports": true } } 

.parcelrc

.parcelrc

json { "extends": "@parcel/config-default", "resolvers": [ "@parcel/resolver-default", "..." ] }  Note: As of recent Parcel versions, enabling package exports is often done by ensuring @parcel/resolver-default is present in the resolvers array. If issues persist, an explicit flag might have been needed in older versions, but extending the default config is the modern approach. The most direct fix cited in GitHub issues [3, 18] involves the package.json method.

After applying one of these configurations and performing the hygiene steps from Step 1, the build should succeed.

5.3 Step 3: The Alternative Solution - Align Code with the react-router v7 Paradigm
This solution is for developers who wish to fully embrace the latest react-router conventions and remove the react-router-dom compatibility layer from their dependencies. This approach makes the code more explicit about its dependencies but requires a project-wide change to import statements.

Rationale: Instead of configuring the toolchain to understand the react-router-dom shim, this method bypasses the shim entirely by using the canonical react-router package directly for all routing APIs.

Implementation Steps:

Uninstall react-router-dom: npm uninstall react-router-dom.[5]

Install react-router: npm install react-router@latest.[5]

Update Application Imports: Perform a search-and-replace across the entire codebase to change all react-router-dom imports to react-router.

Example:

Before: import { createBrowserRouter, RouterProvider, Link } from "react-router-dom";

After: import { createBrowserRouter, RouterProvider, Link } from "react-router"; [5, 12]

5.4 Step 4: The Fallback Strategy - Downgrading for Legacy Compatibility
This approach should be considered a last resort. It is appropriate only for legacy projects where updating the Node.js environment or build tooling is not feasible. Downgrading knowingly incurs technical debt and prevents the project from benefiting from the performance improvements and features of the latest library versions.

Rationale: This method avoids the package.exports issue entirely by reverting to a version of react-router-dom (v6) that used a different architecture and did not rely on this modern resolution feature.

Implementation:

Uninstall the current version: npm uninstall react-router-dom react-router

Install a stable v6 version: npm install react-router-dom@6.[8, 10, 14] This command installs the latest release within the v6 major version.

This will resolve the build error but will require the codebase to adhere to the v6 API, which is different from both v5 and v7.

Section 6: Prophylactic Strategies for Robust Build Systems
Successfully fixing the build error is the immediate goal, but understanding how to prevent this class of problem is the mark of a mature development practice. The following strategies help create more resilient and predictable build systems.

6.1 Embrace a "Tooling as Code" Mindset
A project's build configuration—whether it's .parcelrc, webpack.config.js, or any other tooling file—should be treated as a first-class citizen of the codebase. It is not a "set it and forget it" utility. As dependencies evolve, the tooling that bundles them must also be maintained and understood. Regularly reviewing and understanding the configuration prevents it from becoming a black box that fails in mysterious ways.

6.2 The Importance of Changelogs
Before performing any major version upgrade of a critical dependency like a framework, library, or bundler, developers should make it a standard practice to read the release notes and changelogs. The react-router v7 upgrade guide, for example, details the package simplification and new requirements.[14] A quick review of this document would have immediately highlighted the architectural shift and the need to adjust imports or tooling, preempting the build failure.

6.3 The Power of Lockfiles
The use of lockfiles (package-lock.json, yarn.lock) is non-negotiable for creating deterministic and reproducible builds. A lockfile records the exact version of every dependency and sub-dependency installed. This prevents "dependency drift," where a fresh npm install on a different machine or at a later time could pull in a newer, breaking version of a transitive dependency. A project that "was working few months back and suddenly facing this error now" [21] is a classic symptom of a build environment without a committed lockfile, where a newly installed dependency introduced a breaking change.

6.4 The Developer Experience (DX) Gap in Tooling Diagnostics
Finally,
