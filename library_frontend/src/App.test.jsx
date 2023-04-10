import { test, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import App from "./App";

test("should render Page component", () => {
  render(<App />);
  const pageElement = screen.getByTestId("page");
  expect(pageElement).toBeInTheDocument();
});

// describe(App, () => {
//     it("would have the page component rendered", () => {
//         render(<App />);
//         const pageElement = screen.getByTestId('page');
//         expect(pageElement).toBeInTheDocument();
//     });
// })