/** 
 * Usage: node move.js --name xxx
*/

const path = require("path");
const { promisify } = require("util");
const fs = require("fs");

["readdir", "mkdir", "unlink", "readFile", "writeFile"].forEach(k => {
	const func = fs[k];
	if (typeof func === "function") {
		fs[k] = promisify(func);
	}
});

async function run(params) {
	const homePath = process.env["HOME"];
	if (!homePath) {
		console.log("Unable to get HOME path.");
		process.exit(1);
	}

	const index = process.argv.findIndex(item => item === "--name");
	const weekName = process.argv[index + 1];
	if (index === -1 || !weekName) {
		console.log("Please input the week name.");
		process.exit(1);
	}

	const srcPath = path.join(
		homePath,
		"IdeaProjects/algs-assignment/src",
		weekName
	);

	const distPath = path.resolve(__dirname, "../", weekName);
	try {
		if (fs.existsSync(distPath)) {
			const deleteFiles = await fs.readdir(distPath);
			deleteFiles.forEach(async function(f) {
				await fs.unlink(path.join(distPath, f));
			});
		} else {
			await fs.mkdir(distPath);
		}
	} catch (e) {
		console.log(e);
	}

	try {
		const files = await fs.readdir(srcPath);
		const tasks = files.filter(f => f.split(".")[1] === "java").map(f => {
			return new Promise(async (resolve, reject) => {
				const content = await fs.readFile(
					path.join(srcPath, f),
					"utf8"
				);

				fs
					.writeFile(
						path.join(distPath, f),
						content
							.split("\n")
							.filter(line => !line.includes("package "))
							.join("\n"),
						{
							encoding: "utf8"
						}
					)
					.then(resolve, reject);
			});
		});

		Promise.all(tasks)
			.then(() => {
				console.log("Done.");
			})
			.catch(e => {
				console.log(e);
			});
	} catch (e) {
		console.log(e);
	}
}

run();
