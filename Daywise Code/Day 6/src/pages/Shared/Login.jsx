import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'

import { Button } from "@/components/ui/button"

import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/ui/tabs"
import { Link } from 'react-router-dom'

export function Login() {
  return (
    <div className=' h-full w-full flex justify-center items-center '>
    <Tabs defaultValue="account" className="w-[400px]">
      <TabsList className="grid w-full grid-cols-2">
        <TabsTrigger value="account">User</TabsTrigger>
        <TabsTrigger value="password">Admin</TabsTrigger>
      </TabsList>
      <TabsContent value="account">
        <Card>
          <CardHeader>
            <CardTitle>User Login</CardTitle>
            {/* <CardDescription>
              Make changes to your account here. Click save when you're done.
            </CardDescription> */}
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="space-y-1">
              <Label htmlFor="name">Username</Label>
              <Input id="name" defaultValue="" />
            </div>
            <div className="space-y-1">
              <Label htmlFor="username">Password</Label>
              <Input id="username" type="password" defaultValue="" />
            </div>
          </CardContent>
          <CardFooter>
          <Link className='flex w-full' to ='/dashboard'>
            <Button className='flex w-full'>Login</Button>
            </Link>
          </CardFooter>
        </Card>
      </TabsContent>
      <TabsContent value="password">
        <Card>
          <CardHeader>
            <CardTitle>Admin Login</CardTitle>
            {/* <CardDescription>
              Change your password here. After saving, you'll be logged out.
            </CardDescription> */}
          </CardHeader>
          <CardContent className="space-y-2">
            <div className="space-y-1">
              <Label htmlFor="current">Username</Label>
              <Input id="current" type="password" />
            </div>
            <div className="space-y-1">
              <Label htmlFor="new">Password</Label>
              <Input id="new" type="password" />
            </div>
          </CardContent>
          <CardFooter>
            <Link className='flex w-full' to ='/admin/dashboard'>
            <Button className='flex w-full'>Login</Button>
            </Link>
          </CardFooter>
        </Card>
      </TabsContent>
    </Tabs>
    </div>
  )
}


export default Login